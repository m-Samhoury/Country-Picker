package com.moustafa.countrypicker.utils

import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.target.Target
import com.moustafa.countrypicker.utils.svgloading.GlideApp
import com.moustafa.countrypicker.utils.svgloading.SvgSoftwareLayerSetter


/**
 * @author moustafasamhoury
 * created on Friday, 20 Sep, 2019
 */

fun ImageView.load(
    imageUrl: String,
    onSuccess: (() -> Any?)? = null,
    onFailed: (() -> Any?)? = null
) {
    val requestBuilder: RequestBuilder<PictureDrawable> =
        GlideApp.with(this)
            .`as`(PictureDrawable::class.java)
            .transition(withCrossFade())
            .listener(object : SvgSoftwareLayerSetter() {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<PictureDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onFailed?.invoke()
                    return super.onLoadFailed(e, model, target, isFirstResource)
                }

                override fun onResourceReady(
                    resource: PictureDrawable,
                    model: Any,
                    target: Target<PictureDrawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    onSuccess?.invoke()
                    return super.onResourceReady(
                        resource,
                        model,
                        target,
                        dataSource,
                        isFirstResource
                    )
                }
            })

    requestBuilder.load(Uri.parse(imageUrl)).into(this)
}