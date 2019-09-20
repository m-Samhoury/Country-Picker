package com.moustafa.countrypicker.utils

import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.moustafa.countrypicker.utils.svgloading.GlideApp
import com.moustafa.countrypicker.utils.svgloading.SvgSoftwareLayerSetter


/**
 * @author moustafasamhoury
 * created on Friday, 20 Sep, 2019
 */

fun ImageView.load(imageUrl: String) {

    val requestBuilder: RequestBuilder<PictureDrawable> =
        GlideApp.with(this)
            .`as`(PictureDrawable::class.java)
            .transition(withCrossFade())
            .listener(SvgSoftwareLayerSetter());

    requestBuilder.load(Uri.parse(imageUrl)).into(this)
}