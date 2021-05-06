package com.nandaprasetio.core.misc.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.setImageWithGlide(image: Any?) {
    setImage(this, image)
}

private fun setImage(imageView: ImageView, image: Any?) {
    Glide.with(imageView)
        .load(image)
        .fitCenter()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(imageView)
}