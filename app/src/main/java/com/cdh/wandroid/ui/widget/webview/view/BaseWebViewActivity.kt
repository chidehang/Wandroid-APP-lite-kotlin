package com.cdh.wandroid.ui.widget.webview.view

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.cdh.wandroid.R

/**
 * 通用浏览器父类
 * @author ShaoWenWen
 * @date 2019-09-19
 */
open class BaseWebViewActivity : AppCompatActivity() {
    /**
     * 设置状态栏
     */
    protected fun initStatusBarStyle(fullScreen: Boolean) {
        if (fullScreen) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            val bar = actionBar
            if (actionBar != null) {
                bar!!.hide()
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Android 6.0+
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.WHITE
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                addDefaultStatusBarBackground()
                window.statusBarColor = Color.TRANSPARENT
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
    }

    private fun addDefaultStatusBarBackground() {
        val view = View(this)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            statusBarHeight
        )
        view.setBackgroundColor(resources.getColor(R.color.common_background))
        (window.decorView as ViewGroup).addView(view)
    }//根据资源ID获取响应的尺寸值//获取status_bar_height资源的ID

    /**
     * 获取状态栏高度
     */
    protected val statusBarHeight: Int
        protected get() {
            var statusBarHeight = 0
            //获取status_bar_height资源的ID
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) { //根据资源ID获取响应的尺寸值
                statusBarHeight = resources.getDimensionPixelSize(resourceId)
            }
            if (statusBarHeight <= 0) {
                try {
                    val clazz =
                        Class.forName("com.android.internal.R\$dimen")
                    val `object` = clazz.newInstance()
                    val height = clazz.getField("status_bar_height")[`object`].toString().toInt()
                    statusBarHeight = resources.getDimensionPixelSize(height)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if (statusBarHeight <= 0) {
                statusBarHeight = resources.getDimensionPixelSize(R.dimen.status_bar_height)
            }
            return statusBarHeight
        }
}