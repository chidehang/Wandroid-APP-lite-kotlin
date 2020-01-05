package com.cdh.wandroid.ui.widget.webview.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View.OnKeyListener
import android.webkit.WebView

/**
 * @author ShaoWenWen
 * @date 2019-09-19
 */
class WebViewComponent @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : WebView(context, attrs) {
    private fun initListener() {
        isFocusableInTouchMode = true
        requestFocus()
        setOnKeyListener(OnKeyListener { view, i, keyEvent ->
            if (KeyEvent.ACTION_DOWN == keyEvent.action && canGoBack()) {
                goBack()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun initWebSetting() { // （1）普通设置
        val webSettings = settings
        // 支持缩放，默认为true。是下面那个的前提
        webSettings.setSupportZoom(true)
        // 缩放至屏幕的大小
        webSettings.loadWithOverviewMode = true
        // 将图片调整到适合webview的大小
        webSettings.useWideViewPort = true
        // 设置编码格式
        webSettings.defaultTextEncodingName = "utf-8"
        // 支持自动加载图片
        webSettings.loadsImagesAutomatically = true
        // （2）调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        webSettings.javaScriptEnabled = true
        //（3）有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        webSettings.setAppCacheEnabled(true)
        val appCachePath =
            context.applicationContext.cacheDir.absolutePath
        webSettings.setAppCachePath(appCachePath)
        // （4）html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
//  （5）然后 复写 WebChromeClient的onCreateWindow方法
        webSettings.setSupportMultipleWindows(false)
        webSettings.javaScriptCanOpenWindowsAutomatically = true
    }

    init {
        initWebSetting()
        initListener()
    }
}