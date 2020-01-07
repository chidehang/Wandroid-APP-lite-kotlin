package com.cdh.wandroid.ui.widget.webview.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import androidx.fragment.app.Fragment
import com.cdh.wandroid.R
import com.cdh.wandroid.ui.widget.webview.callback.CustomWebChromeClient
import com.cdh.wandroid.ui.widget.webview.callback.CustomWebDownloadListener
import com.cdh.wandroid.ui.widget.webview.callback.CustomWebViewClient
import com.cdh.wandroid.ui.widget.webview.view.WebTitleBar.OnTitleBarClickListener

/**
 * WebView容器：包含浏览器标题栏和浏览器内容
 * @author ShaoWenWen
 * @date 2019-09-18
 */
class WebViewFragment : Fragment() {
    private var mRoot: View? = null
    /* 浏览器的标题栏组件 */
    private var mWebTitleBar: WebTitleBar? = null
    /* 浏览器组件，继承WebView */
    private var mWebView: WebViewComponent? = null
    /**
     * 浏览器打开失败，错误页面
     */
    private var mErrorView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRoot != null) {
            val viewParent = mRoot!!.parent as ViewGroup
            viewParent?.removeView(mRoot)
            return mRoot
        }
        mRoot = inflater.inflate(R.layout.fragment_webview, container, false)
        initView()
        initListener()
        setWebClient()
        setCookies()
        mWebView!!.loadUrl(arguments!!.getString(URL))
        return mRoot
    }

    private fun initView() {
        mWebTitleBar = mRoot!!.findViewById(R.id.web_title_bar)
        mWebTitleBar?.setCloseButtonStatus(true)
        mWebView = mRoot!!.findViewById(R.id.web_view)
        mErrorView = mRoot!!.findViewById(R.id.web_error_page)
    }

    private fun initListener() {
        mWebTitleBar?.setOnTitleBarClickListener(object : OnTitleBarClickListener {
            override fun onBackClick() {
                if (mWebView!!.canGoBack()) {
                    mWebView!!.goBack()
                } else {
                    if (activity != null) {
                        activity!!.finish()
                    }
                }
            }

            override fun onCloseClick() {
                if (activity != null) {
                    activity!!.finish()
                }
            }
        })
        mErrorView?.setOnClickListener {
            mErrorView?.visibility = View.GONE
            mWebView!!.loadUrl(arguments!!.getString(URL))
        }
    }

    private fun setWebClient() {
        mWebView!!.webViewClient = CustomWebViewClient(mWebTitleBar, mErrorView)
        mWebView!!.webChromeClient = CustomWebChromeClient(mWebTitleBar)
        mWebView!!.setDownloadListener(CustomWebDownloadListener(activity!!))
    }

    private fun setCookies() {
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true)
    }

    override fun onResume() {
        super.onResume()
        mWebView!!.onResume()
        mWebView!!.resumeTimers()
    }

    override fun onPause() {
        super.onPause()
        mWebView!!.onPause()
        mWebView!!.pauseTimers()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mWebView != null) {
            mWebView!!.clearHistory()
            if (mWebView!!.parent != null) {
                (mWebView!!.parent as ViewGroup).removeView(mWebView)
            }
            mWebView!!.loadUrl("about:blank")
            mWebView!!.stopLoading()
            mWebView!!.webViewClient = null
            mWebView!!.destroy()
            mWebView = null
        }
    }

    companion object {
        const val TAG = "WebViewFragment"
        private const val URL = "URL"
        fun getInstance(url: String?): WebViewFragment {
            val webViewFragment = WebViewFragment()
            val bundle = Bundle()
            bundle.putString( URL, url)
            webViewFragment.arguments = bundle
            return webViewFragment
        }
    }
}