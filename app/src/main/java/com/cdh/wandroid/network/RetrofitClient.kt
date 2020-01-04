package com.cdh.wandroid.network

import com.cdh.wandroid.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by chidehang on 2020-01-02
 */
object RetrofitClient {

    private val retrofit: Retrofit
    private var api: ApiService ?= null

    init {
        var okhttp = OkHttpClient()
        if (BuildConfig.DEBUG) {
            var loggin = HttpLoggingInterceptor()
            loggin.setLevel(HttpLoggingInterceptor.Level.BODY)
            okhttp = OkHttpClient.Builder().addInterceptor(loggin).build()
        }

        retrofit = Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()
    }

    fun getApi(): ApiService {
        if (api == null) {
            api = retrofit.create(ApiService::class.java)
        }
        return api!!
    }
}