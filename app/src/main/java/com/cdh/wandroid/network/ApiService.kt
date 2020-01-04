package com.cdh.wandroid.network

import com.cdh.wandroid.model.bean.BannerBean
import com.cdh.wandroid.model.bean.HomeArticlesInfo
import com.cdh.wandroid.network.response.ArrayResponse
import com.cdh.wandroid.network.response.ObjectResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by chidehang on 2020-01-02
 */
interface ApiService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"

        const val API_BANNER = "/banner/json"
        const val API_HOME_ARTICLES = "/article/list/{page}/json"
    }

    @GET(API_BANNER)
    suspend fun getBanners(): ArrayResponse<BannerBean>

    @GET(API_HOME_ARTICLES)
    suspend fun getHomeArticles(@Path("page") page: String): ObjectResponse<HomeArticlesInfo>
}