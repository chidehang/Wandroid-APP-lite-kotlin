package com.cdh.wandroid.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by chidehang on 2020-01-01
 */
class ImageLoader {

    companion object {
        fun display(view: ImageView, url: String) {
            Glide.with(view).load(url).into(view)
        }
    }
}