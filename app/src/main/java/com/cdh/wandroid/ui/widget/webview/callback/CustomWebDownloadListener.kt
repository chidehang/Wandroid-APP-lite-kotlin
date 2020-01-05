package com.cdh.wandroid.ui.widget.webview.callback

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.DownloadListener
import com.cdh.wandroid.util.ContextUtils

/**
 * 自定义下载监听器
 * @author ShaoWenWen
 * @date 2019-09-19
 */
class CustomWebDownloadListener(activity: Activity) : DownloadListener {
    private val mcontext: Context
    override fun onDownloadStart(
        url: String,
        userAgent: String,
        contentDisposition: String,
        mimetype: String,
        contentLength: Long
    ) {
        if (ContextUtils.isContextValid(mcontext)) {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            mcontext.startActivity(intent)
        }
    }

    companion object {
        private const val TAG = "CustomWebDownloadListen"
    }

    init {
        mcontext = activity
    }
}