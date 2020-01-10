package com.cdh.wandroid.ui.databind

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.cdh.wandroid.R

/**
 * Created by chidehang on 2020-01-09
 */
@BindingAdapter("app:collectIcon")
fun collectIcon(view: ImageView, checked: Boolean) {
    val resId = if (checked) R.mipmap.ic_collect_checked else R.mipmap.ic_collect_normal
    view.setImageResource(resId)
}