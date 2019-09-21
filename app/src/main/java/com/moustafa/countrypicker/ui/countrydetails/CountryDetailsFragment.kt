package com.moustafa.countrypicker.ui.countrydetails

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.moustafa.countrypicker.R
import com.moustafa.countrypicker.base.BaseFragment
import com.moustafa.countrypicker.utils.load
import kotlinx.android.synthetic.main.fragment_country_details.*

/**
 * @author moustafasamhoury
 * created on Friday, 20 Sep, 2019
 */

class CountryDetailsFragment : BaseFragment(R.layout.fragment_country_details) {

    private val args: CountryDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateScreen()
    }

    private fun populateScreen() {
        if (args.country.flagUrl?.isNotBlank() == true) {
            imageViewCountryFlag.load(args.country.flagUrl!!)
        }
        textViewCountryName.text = args.country.name
        textViewCountryDescription.text = args.country.description
    }

    override fun setupViews(rootView: View) {

    }

}