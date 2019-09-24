package com.moustafa.countrypicker.utils

import android.animation.Animator
import android.content.res.ColorStateList
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SearchView
import com.moustafa.countrypicker.R

/**
 * @author moustafasamhoury
 * created on Saturday, 21 Sep, 2019
 */

fun GridLayout.addTimeZoneTextView(timeZone: String): TextView {
    val textView = TextView(context, null, R.style.normalTextStyle).apply {
        text = timeZone
    }
    addView(textView)
    return textView
}

fun MenuItem.setExpansionAnimation(
    revealAnimation: () -> Animator,
    collapseAnimation: () -> Animator,
    @DrawableRes searchBarBackground: Int,
    @ColorInt searchCloseButtonColor: Int
) {

    val searchCloseButton =
        actionView?.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
    searchCloseButton?.imageTintList = ColorStateList.valueOf(searchCloseButtonColor)

    val searchBarLayoutTemp = actionView?.findViewById<LinearLayout>(R.id.search_bar)
        ?.findViewById<LinearLayout>(R.id.search_bar)

    searchBarLayoutTemp?.setBackgroundResource(searchBarBackground)
    searchBarLayoutTemp?.visibility = View.GONE
    setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
        var shouldCollapse: Boolean = true

        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
            val searchBarLayout = actionView
            searchBarLayoutTemp?.visibility = View.GONE

            searchBarLayout?.post {
                val revealAnimation = revealAnimation()
                revealAnimation.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {

                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                        searchBarLayoutTemp?.visibility = View.VISIBLE
                    }
                })
                revealAnimation.start()
                shouldCollapse = false
            }
            return true
        }

        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            if (shouldCollapse) {
                shouldCollapse = false
                return true
            }
            val searchBarLayout = (item?.actionView as SearchView?)
                ?.findViewById<LinearLayout>(R.id.search_bar)?.parent as View?

            searchBarLayout?.let {
                val collapseAnimation = collapseAnimation()

                collapseAnimation
                    .addListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(animation: Animator?) {

                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            shouldCollapse = true
                            item?.collapseActionView()
                        }

                        override fun onAnimationCancel(animation: Animator?) {

                        }

                        override fun onAnimationStart(animation: Animator?) {

                        }

                    })
                collapseAnimation.start()
            }
            return false
        }

    })
}