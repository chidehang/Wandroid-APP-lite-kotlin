package com.cdh.wandroid.network.intercept

import com.cdh.wandroid.model.CookieManager
import com.cdh.wandroid.network.ApiService
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by chidehang on 2020-01-07
 */
class AddCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var cookie = CookieManager.getCookie(ApiService.BASE_URL)
        if (cookie != null && cookie.isNotEmpty()) {
            var newRequest = chain.request().newBuilder().header("Cookie", cookie).build()
            return chain.proceed(newRequest)
        }
        return chain.proceed(chain.request())
    }
}