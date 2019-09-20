package com.moustafa.countrypicker.utils.svgloading

import android.graphics.Picture
import android.graphics.drawable.PictureDrawable
import androidx.annotation.Nullable
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import com.caverock.androidsvg.SVG


/**
 * @author moustafasamhoury
 * created on Friday, 20 Sep, 2019
 */


/**
 * Convert the [SVG]'s internal representation to an Android-compatible one ([Picture]).
 */
class SvgDrawableTranscoder : ResourceTranscoder<SVG, PictureDrawable> {
    @Nullable
    override fun transcode(
        toTranscode: Resource<SVG>, options: Options
    ): Resource<PictureDrawable> {
        val svg = toTranscode.get()
        val picture = svg.renderToPicture()
        val drawable = PictureDrawable(picture)
        return SimpleResource(drawable)
    }
}