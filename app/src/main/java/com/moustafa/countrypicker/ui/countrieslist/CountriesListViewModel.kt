package com.moustafa.countrypicker.ui.countrieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moustafa.countrypicker.base.BaseViewModel
import com.moustafa.countrypicker.models.Country
import com.moustafa.countrypicker.repository.Repository
import com.moustafa.countrypicker.repository.network.StateMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author moustafasamhoury
 * created on Tuesday, 10 Sep, 2019
 */
data class CountriesListState(val stateMonitor: StateMonitor<List<Country>> = StateMonitor.Init)

class CountriesListViewModel(
    private val repository: Repository,
    private val countriesListState: CountriesListState = CountriesListState()
) : BaseViewModel() {

    private val _countriesListStateLiveData = MutableLiveData<CountriesListState>()
    val countriesListStateLiveData: LiveData<CountriesListState> = _countriesListStateLiveData

    fun fetchRecipesList() {
        _countriesListStateLiveData.value =
            countriesListState.copy(stateMonitor = StateMonitor.Loading)
        viewModelScope.launch(Dispatchers.Main) {
            val response = repository.fetchCountriesList {
                _countriesListStateLiveData.value =
                    countriesListState.copy(stateMonitor = StateMonitor.Failed(failed = it))
            }
            if (response != null) {
                _countriesListStateLiveData.value =
                    countriesListState.copy(stateMonitor = StateMonitor.Loaded(response))
            }
        }
    }
}
