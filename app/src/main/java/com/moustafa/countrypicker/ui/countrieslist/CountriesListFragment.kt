package com.moustafa.countrypicker.ui.countrieslist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.moustafa.countrypicker.R
import com.moustafa.countrypicker.base.BaseFragment
import com.moustafa.countrypicker.models.Country
import com.moustafa.countrypicker.repository.network.StateMonitor
import com.moustafa.countrypicker.utils.ItemDecorationCustomPaddings
import kotlinx.android.synthetic.main.fragment_countries_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author moustafasamhoury
 * created on Tuesday, 10 Sep, 2019
 */

class CountriesListFragment : BaseFragment(R.layout.fragment_countries_list) {

    private val countriesListViewModel: CountriesListViewModel by viewModel()

    private val countriesListAdapter by lazy {
        CountriesListAdapter(::onRowItemClicked)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countriesListViewModel.countriesListStateLiveData.observe(this, Observer {
            handleState(it)
        })

        fetchCountriesList()
    }

    private fun fetchCountriesList() {
        countriesListViewModel.fetchCountriesList()
    }

    override fun setupViews(rootView: View) {
        recyclerViewCountriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesListAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addItemDecoration(
                ItemDecorationCustomPaddings(
                    top = 8, bottom = 8,
                    start = 16, end = 16
                )
            )
        }
    }

    private fun handleState(countriesListState: CountriesListState) =
        when (val result = countriesListState.stateMonitor) {
            StateMonitor.Loading -> {
                showLoading(true)
            }
            StateMonitor.Init -> {
                showLoading(false)
            }
            is StateMonitor.Loaded -> {
                showLoading(false)
                populateRecipes(result.result)
            }
            is StateMonitor.Failed -> {
                showLoading(false)
                showError(result.failed) {
                    fetchCountriesList()
                }
            }
        }

    private fun populateRecipes(recipesList: List<Country>) =
        countriesListAdapter.submitList(recipesList)

    private fun onRowItemClicked(view: View, position: Int) {
        view.findNavController().navigate(
            CountriesListFragmentDirections
                .actionCountriesListFragmentToCountryDetailsFragment(
                    countriesListAdapter.currentList[position]
                )
        )
    }

    private fun showError(throwable: Throwable, action: (() -> Any)? = null) {
        val snackBar = Snackbar.make(
            constraintLayoutRoot,
            throwable.message ?: getString(R.string.generic_error_unknown),
            Snackbar.LENGTH_SHORT
        )
        if (action != null) {
            snackBar.setAction(R.string.action_retry) {
                action.invoke()
            }
            snackBar.duration = Snackbar.LENGTH_INDEFINITE
        }
        snackBar.show()
    }

    private fun showLoading(shouldShow: Boolean) {
        if (shouldShow && countriesListAdapter.itemCount == 0) {
            progressBarLoadingCountries.show()
        } else {
            progressBarLoadingCountries.hide()
        }
    }
}
