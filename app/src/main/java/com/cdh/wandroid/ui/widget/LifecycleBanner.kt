package com.cdh.wandroid.ui.widget

import android.content.Context
import android.util.AttributeSet
import com.bigkoo.convenientbanner.ConvenientBanner

/**
 * Created by chidehang on 2020-01-04
 */
class LifecycleBanner<T> : ConvenientBanner<T> {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startTurning(3000)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopTurning()
    }
}