package com.cdh.wandroid.ui.widget.webview.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.LayoutWebTitleBarBinding

/**
 * @author ShaoWenWen
 * @date 2019-09-19
 */
class WebTitleBar : LinearLayout {

    private var mOnTitleBarClickListener: OnTitleBarClickListener? = null

    val mBinding: LayoutWebTitleBarBinding

    constructor(context: Context?) : this(context, attrs = null)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_web_title_bar, this, true)

        initView()
        initListener()
    }

    private fun initListener() {
        mBinding.imageBackH5?.setOnClickListener {
            if (mOnTitleBarClickListener != null) {
                mOnTitleBarClickListener!!.onBackClick()
            }
        }
        mBinding.imageCloseH5?.setOnClickListener {
            if (mOnTitleBarClickListener != null) {
                mOnTitleBarClickListener!!.onCloseClick()
            }
        }
        mBinding.imageCollectH5?.setOnClickListener {
            if (mOnTitleBarClickListener != null) {
                mOnTitleBarClickListener!!.onCollectClick()
            }
        }
    }

    private fun initView() {
    }

    fun setTitle(textTitle: String?) {
        mBinding.textTitle!!.text = textTitle
    }

    fun setCloseButtonStatus(visible: Boolean) {
        mBinding.imageCloseH5!!.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setOnTitleBarClickListener(onTitleBarClickListener: OnTitleBarClickListener?) {
        mOnTitleBarClickListener = onTitleBarClickListener
    }

    fun hideProgressBar(anim: Boolean) {
        if (mBinding.pbWeb == null || mBinding.pbWeb.visibility != View.VISIBLE) {
            return
        }
        if (anim) {
            val animation = AlphaAnimation(1f, 0f)
            animation.duration = 1000
            mBinding.pbWeb.startAnimation(animation)
            mBinding.pbWeb.visibility = View.GONE
        } else {
            mBinding.pbWeb.progress = 0
            mBinding.pbWeb.visibility = View.GONE
        }
    }

    fun showProgressBar(progress: Int) {
        if (mBinding.pbWeb != null) {
            if (mBinding.pbWeb.visibility != View.VISIBLE) {
                mBinding.pbWeb.visibility = View.VISIBLE
            }
            mBinding.pbWeb.progress = progress
        }
    }

    interface OnTitleBarClickListener {
        /**
         * H5页面，点击titlebar的回退监听
         */
        fun onBackClick()

        /**
         * 关闭监听
         */
        fun onCloseClick()

        fun onCollectClick()
    }
}