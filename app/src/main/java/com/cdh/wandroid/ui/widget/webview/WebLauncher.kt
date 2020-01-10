package com.cdh.wandroid.ui.widget.webview

import android.app.Activity
import android.content.Intent
import com.cdh.wandroid.ArgumentConstants
import com.cdh.wandroid.ui.widget.webview.view.WebViewActivity
import java.io.Serializable

/**
 * 联盟定制浏览器，打开入口
 * @author ShaoWenWen
 * @date 2019-09-19
 */
object WebLauncher {

    private const val TAG = "WebLauncher"
    const val WEB_URL = ArgumentConstants.EXTRA_WEB_URL

    fun launchWeb(activity: Activity?, url: String?) {
        launchWeb(activity, url, null)
    }

    fun launchWeb(activity: Activity?, url: String?, params: WebParams?) {
        if (activity == null || url == null) {
            return
        }
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra(WEB_URL, url)
        intent.putExtra(ArgumentConstants.EXTRA_WEB_PARAMS, params)
        activity.startActivity(intent)
    }
}

data class WebParams(
    val selfId: Int,
    val articleId: Int = -1,
    val enableCollect: Boolean = false,
    val collectState: Boolean = false,
    val from: From = From.OTHER
) : Serializable

enum class From {
    ARTICLE,
    FAVORITE,
    OTHER
}