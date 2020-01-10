package com.cdh.wandroid.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.HorizontalScrollView
import androidx.recyclerview.widget.RecyclerView
import com.cdh.wandroid.R
import kotlin.math.abs

/**
 * Created by chidehang on 2020-01-10
 */
class SlideMenuLayout : HorizontalScrollView {

    private lateinit var foreView: View
    private lateinit var backView: View

    private var once = true

    private var isOpen = false

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView()
    }

    private fun initView() {
        isHorizontalScrollBarEnabled = false
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        var recyclerView: RecyclerView? = null
        var view = parent
        while (view != null) {
            if (view is RecyclerView) {
                recyclerView = view
                break
            }
            view = view.parent
        }

        recyclerView?.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                closeMenu()
            }
        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (once) {
            once = true
            foreView = findViewById(R.id.slide_foreground_content)
            backView = findViewById(R.id.slide_background_content)
            foreView.layoutParams.width = MeasureSpec.getSize(widthMeasureSpec)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when(ev.action) {
            MotionEvent.ACTION_UP -> {
                if (abs(scrollX) > backView.measuredWidth / 2) {
                    openMenu()
                } else {
                    closeMenu()
                }
                return true
            }
        }
        return super.onTouchEvent(ev)
    }

    fun isOpen(): Boolean {
        return isOpen
    }

    fun openMenu() {
        if (!isOpen()) {
            isOpen = true
            scrollTo(backView.measuredWidth, 0)
        }
    }

    fun closeMenu() {
        if (isOpen()) {
            isOpen = false
            smoothScrollTo(0, 0)
        }
    }
}