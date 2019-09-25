package com.moustafa.countrypicker.base

import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.moustafa.countrypicker.R
import com.moustafa.countrypicker.utils.tintCompat


/**
 * @author moustafasamhoury
 * created on Thursday, 19 Sep, 2019
 */

abstract class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        ViewCompat.requestApplyInsets(view)
    }

    /**
     * This function is called at the appropriate time to initialise the views
     * i.e. set listeners...
     *
     * @param rootView – The fragment's root view
     */
    protected abstract fun setupViews(rootView: View)

}
//Auxiliary helper methods

fun BaseFragment.supportActionBar() = (activity as? AppCompatActivity)?.supportActionBar

/**
 *  sets the status bar color for lollipop+
 */
fun BaseFragment.setStatusBarColor(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = color
    }
}

fun BaseFragment.setTitleActionBarColor(@ColorInt color: Int) {
    val oldTitle = supportActionBar()?.title
    val span = SpannableStringBuilder.valueOf(oldTitle)
    span.setSpan(
        ForegroundColorSpan(color),
        0,
        oldTitle!!.length,
        SpannableStringBuilder.SPAN_INCLUSIVE_EXCLUSIVE
    )
    supportActionBar()?.title = span
}

fun BaseFragment.setBackArrowActionBarColor(@ColorInt color: Int) {
    val upArrow =
        ContextCompat.getDrawable(activity!!, R.drawable.abc_ic_ab_back_material) //Ugly hack
    upArrow?.tintCompat(color)
    supportActionBar()?.setHomeAsUpIndicator(upArrow)
}
