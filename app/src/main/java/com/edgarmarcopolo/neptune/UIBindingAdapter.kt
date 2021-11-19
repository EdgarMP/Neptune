package com.edgarmarcopolo.neptune

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop

object UIBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["android:image_url"])
    fun loadImage(view: ImageView, url: String? = null) {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.ic_outline_broken_image_24)
            .error(R.drawable.ic_outline_broken_image_24)
            .transform(CenterCrop())
            .into(view)
    }

}