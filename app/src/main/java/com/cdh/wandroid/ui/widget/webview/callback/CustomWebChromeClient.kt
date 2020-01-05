package com.cdh.wandroid.ui.widget.webview.callback

import android.os.Message
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebView.WebViewTransport
import com.cdh.wandroid.ui.widget.webview.view.WebTitleBar

/**
 * @author ShaoWenWen
 * @date 2019-09-19
 */
class CustomWebChromeClient(private val mWebTitleBar: WebTitleBar?) : WebChromeClient() {
    override fun onReceivedTitle(view: WebView, title: String) {
        super.onReceivedTitle(view, title)
        Log.e(TAG, "onReceivedTitle: $title")
        mWebTitleBar?.setTitle(title)
    }

    override fun onProgressChanged(view: WebView, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (newProgress == 100) {
            mWebTitleBar?.hideProgressBar(true)
        } else {
            mWebTitleBar?.showProgressBar(newProgress)
        }
    }

    override fun onCreateWindow(
        view: WebView,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message
    ): Boolean {
        val transport = resultMsg.obj as WebViewTransport
        transport.webView = view
        resultMsg.sendToTarget()
        return true
    }

    companion object {
        private const val TAG = "CustomWebChromeClient"
    }

}