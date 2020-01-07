package com.cdh.wandroid.model

/**
 * Created by chidehang on 2020-01-07
 */
object CookieManager {

    private var cookieMap = mutableMapOf<String, String>()

    fun putCookie(host: String, cookie: String) {
        cookieMap.put(host, cookie)
    }

    fun getCookie(host: String): String? {
        return cookieMap[host]
    }
}