package com.cdh.wandroid.ui.widget.webview.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.cdh.wandroid.R

/**
 * @author ShaoWenWen
 * @date 2019-09-19
 */
class WebTitleBar : LinearLayout {
    private var mImageBack: ImageButton? = null
    private var mImageClose: ImageButton? = null
    private var mTextTitle: TextView? = null
    private var mProgressBar: ProgressBar? = null
    private var mOnTitleBarClickListener: OnTitleBarClickListener? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        View.inflate(context, R.layout.layout_web_title_bar, this)
        initView()
        initListener()
    }

    private fun initListener() {
        mImageBack!!.setOnClickListener {
            if (mOnTitleBarClickListener != null) {
                mOnTitleBarClickListener!!.onBackClick()
            }
        }
        mImageClose!!.setOnClickListener {
            if (mOnTitleBarClickListener != null) {
                mOnTitleBarClickListener!!.onCloseClick()
            }
        }
    }

    private fun initView() {
        mImageBack = findViewById(R.id.image_back_h5)
        mImageClose = findViewById(R.id.image_close_h5)
        mTextTitle = findViewById(R.id.text_title)
        mProgressBar = findViewById(R.id.pb_web)
    }

    fun setTitle(textTitle: String?) {
        mTextTitle!!.text = textTitle
    }

    fun setCloseButtonStatus(visible: Boolean) {
        mImageClose!!.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setOnTitleBarClickListener(onTitleBarClickListener: OnTitleBarClickListener?) {
        mOnTitleBarClickListener = onTitleBarClickListener
    }

    fun hideProgressBar(anim: Boolean) {
        if (mProgressBar == null || mProgressBar!!.visibility != View.VISIBLE) {
            return
        }
        if (anim) {
            val animation = AlphaAnimation(1f, 0f)
            animation.duration = 1000
            mProgressBar?.startAnimation(animation)
            mProgressBar?.visibility = View.GONE
        } else {
            mProgressBar?.progress = 0
            mProgressBar?.visibility = View.GONE
        }
    }

    fun showProgressBar(progress: Int) {
        if (mProgressBar != null) {
            if (mProgressBar!!.visibility != View.VISIBLE) {
                mProgressBar!!.visibility = View.VISIBLE
            }
            mProgressBar!!.progress = progress
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
    }
}