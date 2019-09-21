package com.moustafa.countrypicker.ui.countrydetails

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.moustafa.countrypicker.R
import com.moustafa.countrypicker.base.BaseFragment
import com.moustafa.countrypicker.utils.ItemDecorationCustomPaddings
import com.moustafa.countrypicker.utils.formatted
import com.moustafa.countrypicker.utils.load
import com.moustafa.countrypicker.utils.px
import kotlinx.android.synthetic.main.fragment_country_details.*

/**
 * @author moustafasamhoury
 * created on Friday, 20 Sep, 2019
 */

class CountryDetailsFragment : BaseFragment(R.layout.fragment_country_details) {

    private val args: CountryDetailsFragmentArgs by navArgs()
    private val regionalBlocsListAdapter by lazy {
        RegionalBlocsListAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateScreen()
    }

    private fun populateScreen() {
        if (args.country.flagUrl?.isNotBlank() == true) {
            imageViewCountryFlag.load(args.country.flagUrl!!)
        }

        if (args.country.name?.isNotBlank() == true) {
            textViewCountryName.text = args.country.name
        } else {
            textViewCountryName.visibility = View.GONE
            textViewCountryNameLabel.visibility = View.GONE
        }

        if (args.country.description?.isNotBlank() == true) {
            textViewCountryDescription.text = args.country.description
        } else {
            textViewCountryDescription.visibility = View.INVISIBLE
            textViewCountryDescriptionLabel.visibility = View.INVISIBLE
        }

        if (args.country.timeZones?.isNotEmpty() == true) {
            args.country.timeZones?.forEachIndexed { index: Int, timeZone: String ->
                val textView = TextView(context, null, R.style.normalTextStyle).apply {
                    text = timeZone
                }
                flowLayoutTimeZones.addView(textView)

                if (index % 2 == 1) {
                    (textView.layoutParams as GridLayout.LayoutParams)
                        .setMargins(16.px(), 0, 0, 0)
                }
            }
        } else {
            textViewTimeZonesLabel.visibility = View.GONE
            textViewTimeZones.visibility = View.GONE
        }

        if (args.country.population != null) {
            textViewPopulation.text = args.country.population!!.formatted()
        } else {
            textViewPopulationLabel.visibility = View.GONE
            textViewPopulation.visibility = View.GONE
        }

        textViewRegionsLabel.visibility =
            if (args.country.regionalBlocs.isNullOrEmpty()) View.GONE else View.VISIBLE
        regionalBlocsListAdapter.submitList(args.country.regionalBlocs)

    }

    override fun setupViews(rootView: View) {
        recyclerViewRegionalBlocs.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = regionalBlocsListAdapter
            addItemDecoration(
                ItemDecorationCustomPaddings(
                    top = 8, bottom = 8,
                    start = 16, end = 16
                )
            )
        }
    }

}