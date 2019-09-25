package com.moustafa.countrypicker.ui.countrydetails

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Target
import androidx.recyclerview.widget.LinearLayoutManager
import com.moustafa.countrypicker.R
import com.moustafa.countrypicker.base.*
import com.moustafa.countrypicker.repository.Repository
import com.moustafa.countrypicker.utils.*
import kotlinx.android.synthetic.main.fragment_country_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * @author moustafasamhoury
 * created on Friday, 20 Sep, 2019
 */

class CountryDetailsFragment : BaseFragment(R.layout.fragment_country_details) {

    private val args: CountryDetailsFragmentArgs by navArgs()
    private val regionalBlocsListAdapter by lazy {
        RegionalBlocsListAdapter()
    }

    private var generatedColors: Triple<ActionBarColor, StatusBarColor, TitleActionBarColor>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateScreen()
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

    private fun populateScreen() {
        populateToolbarTitle()
        loadFlagImage()
        populateCountryName()
        populateCountryDescription()
        populateTimeZones()
        populatePopulation()
        populateRegionalBlocs()
        loadStaticMap()
    }

    private fun populateToolbarTitle() {
        supportActionBar()?.title = args.country.name
    }

    private fun loadStaticMap() {
        imageViewCountryStaticMap.post {
            val staticMapUrl = Repository.formatStaticMapUrl(
                mapLatLng = args.country.mapLatLng,
                width = imageViewCountryStaticMap.width,
                height = imageViewCountryStaticMap.height
            )
            if (staticMapUrl?.isNotBlank() == true) {
                imageViewCountryStaticMap.load(
                    imageUrl = staticMapUrl,
                    roundedCorners = 8.px(), onSuccess = {
                        progressBarLoadingCountryMap.visibility = View.GONE
                    }, onFailed = {
                        progressBarLoadingCountryMap.visibility = View.GONE
                    }
                )
            } else {
                imageViewCountryStaticMap.visibility = View.GONE
                progressBarLoadingCountryMap.visibility = View.GONE
            }
        }
    }

    private fun populateRegionalBlocs() {
        textViewRegionsLabel.visibility =
            if (args.country.regionalBlocs.isNullOrEmpty()) View.GONE else View.VISIBLE
        regionalBlocsListAdapter.submitList(args.country.regionalBlocs)
    }

    private fun populatePopulation() {
        if (args.country.population != null) {
            textViewPopulation.text = args.country.population!!.formatted()
        } else {
            textViewPopulationLabel.visibility = View.GONE
            textViewPopulation.visibility = View.GONE
        }
    }

    private fun populateTimeZones() {
        if (args.country.timeZones?.isNotEmpty() == true) {
            args.country.timeZones?.forEachIndexed { index: Int, timeZone: String ->
                val textView = flowLayoutTimeZones.addTimeZoneTextView(timeZone)

                if (index % 2 == 1) {//only apply margin to the second row i.e index 1, 3...
                    (textView.layoutParams as? GridLayout.LayoutParams)
                        ?.setMargins(16.px(), 0, 0, 0)
                }
            }
        } else {
            textViewTimeZonesLabel.visibility = View.GONE
            textViewTimeZones.visibility = View.GONE
        }
    }

    private fun populateCountryDescription() {
        if (args.country.description?.isNotBlank() == true) {
            textViewCountryDescription.text = args.country.description
        } else {
            textViewCountryDescription.visibility = View.INVISIBLE
            textViewCountryDescriptionLabel.visibility = View.INVISIBLE
        }
    }

    private fun populateCountryName() {
        if (args.country.name?.isNotBlank() == true) {
            textViewCountryName.text = args.country.name
        } else {
            textViewCountryName.visibility = View.GONE
            textViewCountryNameLabel.visibility = View.GONE
        }
    }

    private fun loadFlagImage() {
        if (args.country.flagUrl?.isNotBlank() == true) {
            imageViewCountryFlag.loadSVG(args.country.flagUrl!!, onSuccess = { drawable ->
                viewLifecycleOwner.lifecycleScope.launch {
                    generatedColors = generateColors(
                        drawable
                    )
                    if (generatedColors != null) {
                        val (actionBarColor, statusBarColor, titleActionBarColor) = generatedColors!!

                        animateActionBarColor(
                            fromColor = ContextCompat.getColor(context!!, R.color.colorPrimary),
                            toColor = actionBarColor.color
                        )
                        animateStatusBarColor(statusBarColor)
                        animateTitleActionBarColor(titleActionBarColor)
                        animateBackArrouActionBarColor(titleActionBarColor)
                    }
                }
            })
        } else {
            imageViewCountryFlag.visibility = View.GONE
        }
    }

    private fun animateBackArrouActionBarColor(titleActionBarColor: TitleActionBarColor) {
        ColorUtils.animateColors(
            fromColor = ContextCompat.getColor(context!!, R.color.white),
            toColor = titleActionBarColor.color
        ) {
            setBackArrowActionBarColor(it)
        }
    }

    private fun animateTitleActionBarColor(titleActionBarColor: TitleActionBarColor) {
        ColorUtils.animateColors(
            fromColor = ContextCompat.getColor(context!!, R.color.white),
            toColor = titleActionBarColor.color
        ) {
            setTitleActionBarColor(it)
        }
    }

    private fun animateStatusBarColor(statusBarColor: StatusBarColor) {
        ColorUtils.animateColors(
            fromColor = ContextCompat.getColor(context!!, R.color.colorPrimaryDark),
            toColor = statusBarColor.color
        ) {
            setStatusBarColor(it)
        }
    }

    private fun animateActionBarColor(@ColorInt fromColor: Int, @ColorInt toColor: Int) {
        val actionBar = supportActionBar()
        ColorUtils.animateColors(
            fromColor = fromColor,
            toColor = toColor
        ) {
            actionBar?.setBackgroundDrawable(ColorDrawable(it))
        }
    }

    private suspend fun generateColors(drawable: Drawable?):
            Triple<ActionBarColor, StatusBarColor, TitleActionBarColor>? =
        withContext(Dispatchers.Default) {

            if (drawable == null) {
                return@withContext null
            }
            val palette = Palette
                .from(drawable.toBitmap())
                .addTarget(Target.VIBRANT)
                .generate()

            if (palette.dominantSwatch == null) {
                return@withContext null
            }

            val actionBarColor = palette.dominantSwatch!!.rgb
            val statusBarColor = ColorUtils.manipulateColor(actionBarColor, 0.7f)
            val titleActionBarColor = palette.dominantSwatch!!.bodyTextColor
            return@withContext Triple(
                ActionBarColor(actionBarColor),
                StatusBarColor(statusBarColor),
                TitleActionBarColor(titleActionBarColor)
            )
        }

    override fun onDestroyView() {

        setStatusBarColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))

        if (generatedColors != null) {
            animateActionBarColor(
                fromColor = generatedColors!!.first.color,
                toColor = ContextCompat.getColor(context!!, R.color.colorPrimary)
            )
        } else {
            supportActionBar()?.setBackgroundDrawable(
                ColorDrawable(ContextCompat.getColor(context!!, R.color.colorPrimary))
            )
        }

        super.onDestroyView()
    }
}

inline class ActionBarColor(@ColorInt val color: Int)
inline class StatusBarColor(@ColorInt val color: Int)
inline class TitleActionBarColor(@ColorInt val color: Int)