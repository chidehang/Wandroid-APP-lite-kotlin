package com.cdh.wandroid.ui.widget.webview

import android.app.Activity
import android.content.Intent
import com.cdh.wandroid.ui.widget.webview.view.WebViewActivity

/**
 * 联盟定制浏览器，打开入口
 * @author ShaoWenWen
 * @date 2019-09-19
 */
object WebLauncher {
    private const val TAG = "WebLauncher"
    const val WEB_URL = "web_url"
    /**
     * 联盟定制浏览器，打开入口
     */
    fun launchWeb(activity: Activity?, url: String?) {
        if (activity == null || url == null) {
            return
        }
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra(WEB_URL, url)
        activity.startActivity(intent)
    }
}