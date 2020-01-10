package com.cdh.wandroid.model

import com.cdh.wandroid.WandroidApp
import com.cdh.wandroid.util.PreferenceUtils

/**
 * Created by chidehang on 2020-01-07
 */
object CookieManager {

    private var cookieMap = mutableMapOf<String, String>()

    fun putCookie(host: String, cookie: String) {
        cookieMap[host] = cookie
        PreferenceUtils.setPrefString(WandroidApp.applicationContext, host, cookie)
    }

    fun getCookie(host: String): String? {
        var cookie = cookieMap[host]
        if (cookie == null || cookie.isEmpty()) {
            cookie = PreferenceUtils.getPrefString(WandroidApp.applicationContext, host, "")
            if (cookie != null) {
                cookieMap[host] = cookie
            }
        }
        return cookie
    }

    fun removeCookie(host: String) {
        PreferenceUtils.setPrefString(WandroidApp.applicationContext, host, "")
        cookieMap.remove(host)
    }
}