package com.cdh.wandroid.network.intercept

import com.cdh.wandroid.model.CookieManager
import com.cdh.wandroid.network.ApiService
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by chidehang on 2020-01-07
 */
class ReceiveCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())
        var list = response.headers("Set-Cookie")
        if (list != null && list.isNotEmpty()) {
            var cookie = ""
            for (str in list) {
                cookie += str
            }
            CookieManager.putCookie(ApiService.BASE_URL, cookie)
        }
        return response
    }
}