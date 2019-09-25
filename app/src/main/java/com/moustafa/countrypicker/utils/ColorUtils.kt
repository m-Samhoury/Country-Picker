package com.moustafa.countrypicker.utils

import android.animation.ValueAnimator
import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.math.roundToInt

/**
 * @author moustafasamhoury
 * created on Wednesday, 25 Sep, 2019
 */

object ColorUtils {
    /**
     * Provides a darker version of a given [color]
     * @Link{https://stackoverflow.com/questions/33072365/how-to-darken-a-given-color-int}
     * @param color color provided
     * @param factor factor to make color darker
     * @return int as darker color
     */
    fun manipulateColor(@ColorInt color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = (Color.red(color) * factor).roundToInt()
        val g = (Color.green(color) * factor).roundToInt()
        val b = (Color.blue(color) * factor).roundToInt()
        return Color.argb(
            a,
            r.coerceAtMost(255),
            g.coerceAtMost(255),
            b.coerceAtMost(255)
        )
    }

    /**
     * Adds smooth intermediate colors animation till it reah the toColor.
     * [animate] is invoked along the way as a callback to be used to update the color of
     * the view itself
     */
    fun animateColors(@ColorInt fromColor: Int, @ColorInt toColor: Int, animate: (Int) -> Unit) {
        val colorAnimator = ValueAnimator.ofArgb(fromColor, toColor)
        colorAnimator.addUpdateListener {
            animate(it.animatedValue as Int)
        }

        colorAnimator.start()
    }
}