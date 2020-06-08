package com.technorely.lastfm.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load

class DataBindingAdapters {

    companion object {
        @BindingAdapter("app:imageResource")
        @JvmStatic
        fun setImageResource(imageView: ImageView, resource: Int) {
            imageView.setImageResource(resource)
        }

        @BindingAdapter("app:imageCoil")
        @JvmStatic
        fun setImageCoil(imageView: ImageView, resource: String) {
            imageView.load(resource)
        }
    }
}