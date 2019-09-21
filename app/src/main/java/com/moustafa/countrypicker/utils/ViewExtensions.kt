package com.moustafa.countrypicker.utils

import android.widget.GridLayout
import android.widget.TextView
import com.moustafa.countrypicker.R

/**
 * @author moustafasamhoury
 * created on Saturday, 21 Sep, 2019
 */

fun GridLayout.addTimeZoneTextView(timeZone: String): TextView =
    TextView(context, null, R.style.normalTextStyle).apply {
        text = timeZone
    }
