package com.cdh.wandroid.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.children
import com.cdh.wandroid.WandroidApp
import com.cdh.wandroid.util.DeviceUtils

/**
 * Created by chidehang on 2020-01-06
 */
class FlowLayout : ViewGroup {

    private var lineSpacing: Int = DeviceUtils.dip2px(WandroidApp.applicationContext, 5f)
    private var itemSpacing: Int = DeviceUtils.dip2px(WandroidApp.applicationContext, 5f)

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)

        val height = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        // 该流式布局宽度
        val maxWidth = if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.EXACTLY) width else Int.MAX_VALUE

        // 初始子view四个边界位置（计算加入padding）
        var childLeft = paddingLeft
        var childTop = paddingTop
        var childRight = childLeft
        var childBottom = childTop
        // 最宽行的view的右边界（设置刚好wrap该view时的尺寸）
        var maxChildRight = 0
        // 子view可以达到的最大右边界（超出该边界则换行）
        val maxRight = maxWidth - paddingRight

        for ((index, child) in children.withIndex()) {
            if (child.visibility == View.GONE) {
                // gone的view不占位置
                continue
            }
            // 测量child（之后可获取child尺寸）
            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            var lp = child.layoutParams
            var leftMargin = 0
            var rightMargin = 0
            // 获取child的左右margin
            if (lp is MarginLayoutParams) {
                leftMargin += lp.leftMargin
                rightMargin += lp.rightMargin
            }

            // 计算child右边界
            childRight = childLeft + child.measuredWidth + leftMargin

            if (childRight > maxRight) {
                // 超出允许右边界，换行
                childLeft = paddingLeft
                childTop = childBottom + lineSpacing
            }

            // 再次计算child右边界，并且加上item间隔
            childRight = childLeft + child.measuredWidth + leftMargin + itemSpacing
            // 计算child下边界
            childBottom = childTop + child.measuredHeight

            if (childRight > maxChildRight) {
                // 更新child中达到的最大右边界
                maxChildRight = childRight
            }

            // 偏移至下一个child左边界
            childLeft += leftMargin + child.measuredWidth + rightMargin + itemSpacing

            if (index == (childCount - 1)) {
                // 遍历至最后一个child时，补上右margin
                maxChildRight += rightMargin
            }
        }

        // 计算加入右、下padding
        maxChildRight += paddingRight
        childBottom += paddingBottom

        // 计算最终该流式布局尺寸
        val finalWidth = getMeasuredDimension(width, widthMode, maxChildRight)
        val finalHeight = getMeasuredDimension(height, heightMode, childBottom)
        setMeasuredDimension(finalWidth, finalHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount == 0) {
            return
        }

        var isRTL = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL
        // 获取起止padding
        var paddingStart = if (isRTL) paddingRight else paddingLeft
        var paddingEnd = if (isRTL) paddingLeft else paddingRight
        // 初始child四边界位置
        var childStart = paddingStart
        var childTop = paddingTop
        var childEnd = childStart
        var childBottom = childTop

        // 最大宽度（超过则换行）
        var maxChildEnd = right - left - paddingEnd

        for ((index, child) in children.withIndex()) {
            if (child.visibility == View.GONE) {
                continue
            }

            // 获取child的起止margin
            var lp = child.layoutParams
            var startMargin = 0
            var endMargin = 0
            if (lp is MarginLayoutParams) {
                startMargin += MarginLayoutParamsCompat.getMarginStart(lp)
                endMargin += MarginLayoutParamsCompat.getMarginEnd(lp)
            }

            // 计算child截止边界
            childEnd = childStart + child.measuredWidth + startMargin

            // 若超出边界，则换行
            if (childEnd > maxChildEnd) {
                childStart = paddingStart
                childTop = childBottom + lineSpacing
            }

            // 重新计算child截止边界
            childEnd = childStart + child.measuredWidth + startMargin
            // 计算child下边界
            childBottom = childTop + child.measuredHeight

            if (isRTL) {
                child.layout(maxChildEnd - childEnd, childTop, maxChildEnd - childStart - startMargin, childBottom)
            } else {
                child.layout(childStart + startMargin, childTop, childEnd, childBottom)
            }

            childStart += startMargin + child.measuredWidth + endMargin + itemSpacing
        }
    }

    companion object {
        private fun getMeasuredDimension(size: Int, mode: Int, childrenEdge: Int): Int {
            when(mode) {
                MeasureSpec.EXACTLY -> {
                    return size
                }
                MeasureSpec.AT_MOST -> {
                     return Math.min(size, childrenEdge)
                }
                else -> {
                    return childrenEdge
                }
            }
        }
    }
}