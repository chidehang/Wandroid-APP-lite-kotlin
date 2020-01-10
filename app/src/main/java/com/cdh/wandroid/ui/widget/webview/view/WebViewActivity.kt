package com.cdh.wandroid.ui.widget.webview.view

import android.os.Bundle
import android.view.View
import com.cdh.wandroid.ArgumentConstants
import com.cdh.wandroid.R
import com.cdh.wandroid.ui.widget.webview.WebLauncher
import com.cdh.wandroid.ui.widget.webview.WebParams

/**
 * 浏览器Activity：用于打开H5页面
 * @author ShaoWenWen
 * @date 2019-09-19
 */
class WebViewActivity : BaseWebViewActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        initStatusBarStyle(false)
        findViewById<View>(R.id.frame_root).setPadding(0, statusBarHeight, 0, 0)
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.frame_root,
                WebViewFragment.getInstance(
                    intent.getStringExtra(WebLauncher.WEB_URL),
                    intent.getSerializableExtra(ArgumentConstants.EXTRA_WEB_PARAMS) as WebParams?
                ),
                WebViewFragment.TAG
            )
            .commitAllowingStateLoss()
    }
}