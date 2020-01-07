package com.cdh.wandroid.ui.widget

import android.app.Activity
import android.content.Context
import android.database.DatabaseUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.LayoutCommonTitleBarBinding

/**
 * Created by chidehang on 2020-01-07
 */
class CommonTitleBar : RelativeLayout {

    private lateinit var mBinding: LayoutCommonTitleBarBinding

    constructor(context: Context?) : super(context) {
        initView(context, null, 0)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs, 0)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs, defStyleAttr)
    }

    private fun initView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_common_title_bar, this, true)

        mBinding.imageBack.setOnClickListener {
            if (context is Activity) {
                context.onBackPressed()
            }
        }
    }

    fun setTitle(title: String) {
        mBinding.textTitle.text = title
    }
}