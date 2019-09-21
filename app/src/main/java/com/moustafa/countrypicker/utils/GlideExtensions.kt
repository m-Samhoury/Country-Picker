package com.moustafa.countrypicker.utils

import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.moustafa.countrypicker.R
import com.moustafa.countrypicker.utils.svgloading.GlideApp
import com.moustafa.countrypicker.utils.svgloading.GlideRequest
import com.moustafa.countrypicker.utils.svgloading.SvgSoftwareLayerSetter


/**
 * @author moustafasamhoury
 * created on Friday, 20 Sep, 2019
 */
/**
 * Loads svg file from the web into the imageview using [imageUrl].
 * Also provides [onSuccess] and [onFailed] callback optional methods
 */
fun ImageView.loadSVG(
    imageUrl: String,
    onSuccess: (() -> Any?)? = null,
    onFailed: (() -> Any?)? = null
) {
    val requestBuilder: RequestBuilder<PictureDrawable> =
        GlideApp.with(this)
            .`as`(PictureDrawable::class.java)
            .transition(withCrossFade())
            .error(R.drawable.ic_flag)
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

/**
 * Loads image file from the web into the imageview using [imageUrl].
 * Providing [roundedCorners] will be applied as a radius to the image corners
 * Also provides [onSuccess] and [onFailed] callback optional methods
 */
fun ImageView.load(
    imageUrl: String,
    roundedCorners: Int? = null,
    onSuccess: (() -> Any?)? = null,
    onFailed: (() -> Any?)? = null
) {
    var requestBuilder: GlideRequest<Drawable> = GlideApp.with(this)
        .load(imageUrl)
        .transition(withCrossFade())

    if (roundedCorners != null) {
        requestBuilder = requestBuilder.apply(RequestOptions().transform(RoundedCorners(8.px())))
    }

    requestBuilder = requestBuilder.listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onFailed?.invoke()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onSuccess?.invoke()
            return false
        }
    })
    requestBuilder.load(Uri.parse(imageUrl)).into(this)
}