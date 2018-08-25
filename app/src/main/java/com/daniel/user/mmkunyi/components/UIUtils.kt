package com.daniel.user.mmkunyi.components

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.bumptech.glide.request.target.BitmapImageViewTarget

class UIUtils {
    companion object {
        fun getRoundedImageTarget(context: Context, imageView: ImageView,
                                  radius: Float): BitmapImageViewTarget {
            return object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.cornerRadius = radius
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            }
        }
    }

}
