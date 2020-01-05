package com.cdh.wandroid.ui.widget.webview.callback

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.*
import com.cdh.wandroid.ui.widget.webview.view.WebTitleBar

/**
 * @author ShaoWenWen
 * @date 2019-09-19
 */
class CustomWebViewClient(private val mWebTitleBar: WebTitleBar?, private val mErrorView: View?) :
    WebViewClient() {

    /**
     * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
     */
    override fun shouldOverrideUrlLoading(
        view: WebView,
        url: String
    ): Boolean {
        return try {
            if (URLUtil.isValidUrl(url)) {
                view.loadUrl(url)
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                if (null != intent.resolveActivity(view.context.applicationContext.packageManager)) {
                    if (view.context !is Activity) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    view.context.startActivity(intent)
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun onPageStarted(
        view: WebView?,
        url: String?,
        favicon: Bitmap?
    ) {
        mWebTitleBar?.showProgressBar(0)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        mWebTitleBar?.hideProgressBar(true)
    }

    override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest,
        error: WebResourceError
    ) {
        super.onReceivedError(view, request, error)
        mWebTitleBar?.hideProgressBar(true)
        if (null != view) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 网页无法打开，没有匹配到host时，不会回调http error
                val settings = view.settings
                settings.javaScriptEnabled = true
                view.evaluateJavascript(
                    "javascript:document.body.innerHTML"
                ) { value ->
                    // 网页无法打开时，js获取到的值
                    if (TextUtils.isEmpty(value) || value.equals(
                            "null",
                            ignoreCase = true
                        ) || value.equals("\"\"", ignoreCase = true)
                        || value.contains("\\u003Ch2>网页无法打开\\u003C/h2>\\n")
                    ) {
                        showRefreshView(view, true)
                    }
                }
            }
        }
    }

    override fun onReceivedHttpError(
        view: WebView,
        request: WebResourceRequest,
        errorResponse: WebResourceResponse
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (null != errorResponse && errorResponse.statusCode >= 400 && errorResponse.statusCode <= 599 && null != request && request.isForMainFrame
            ) {
                showRefreshView(view, true)
            }
        } else {
            showRefreshView(view, true)
        }
    }

    /**
     * @param view webview
     * @param shouldLoadBlank 是否手动打开空白页，避免有404类似的错误页
     */
    private fun showRefreshView(view: WebView?, shouldLoadBlank: Boolean) {
        if (null != mErrorView) {
            mErrorView.visibility = View.VISIBLE
        }
        if (shouldLoadBlank && null != view) {
            view.loadUrl("about:blank")
        }
    }

    companion object {
        private const val TAG = "CustomWebViewClient"
    }

}