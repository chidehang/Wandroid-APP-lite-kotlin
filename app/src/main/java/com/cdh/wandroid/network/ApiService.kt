package com.cdh.wandroid.network

import com.cdh.wandroid.model.bean.*
import com.cdh.wandroid.network.response.ArrayResponse
import com.cdh.wandroid.network.response.ObjectResponse
import retrofit2.http.*

/**
 * Created by chidehang on 2020-01-02
 */
interface ApiService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"

        const val API_BANNER = "/banner/json"
        const val API_HOME_ARTICLES = "/article/list/{page}/json"
        const val API_CATEGORY_TREE = "/tree/json"
        const val API_ARTICLES_BY_CATEGORY = "/article/list/{page}/json"
        const val API_LOGIN = "/user/login"
        const val API_REGISTER = "/user/register"
        const val API_LOGOUT = "/user/logout/json"
        const val API_HOT_KEYS = "/hotkey/json"
        const val API_SEARCH = "/article/query/{page}/json"
        const val API_FAVORITE = "/lg/collect/list/{page}/json"
        const val API_COLLECT = "/lg/collect/{id}/json"
        const val API_UNCOLLECT_ARTICLE = "/lg/uncollect_originId/{id}/json"
        const val API_UNCOLLECT_MY = "/lg/uncollect/{id}/json"
    }

    @GET(API_BANNER)
    suspend fun getBanners(): ArrayResponse<BannerBean>

    @GET(API_HOME_ARTICLES)
    suspend fun getHomeArticles(@Path("page") page: String): ObjectResponse<ArticlesPageInfo>

    @GET(API_CATEGORY_TREE)
    suspend fun getCategoryTree(): ArrayResponse<CategoryBean>

    @GET(API_ARTICLES_BY_CATEGORY)
    suspend fun getArticlesByCategory(@Path("page") page: Int, @Query("cid") cid: Int)
            : ObjectResponse<ArticlesPageInfo>

    @POST(API_LOGIN)
    @FormUrlEncoded
    suspend fun login(@FieldMap params: Map<String, String>): ObjectResponse<AccountBean>

    @POST(API_REGISTER)
    @FormUrlEncoded
    suspend fun register(@FieldMap params: Map<String, String>): ObjectResponse<AccountBean>

    @GET(API_LOGOUT)
    suspend fun logout(): ObjectResponse<Any>

    @GET(API_HOT_KEYS)
    suspend fun getHotKeys(): ArrayResponse<SearchKeyBean>

    @POST(API_SEARCH)
    @FormUrlEncoded
    suspend fun search(@Path("page") page: Int, @FieldMap params: Map<String, String>): ObjectResponse<ArticlesPageInfo>

    @GET(API_FAVORITE)
    suspend fun getMyFavoriteArticles(@Path("page") page: Int): ObjectResponse<ArticlesPageInfo>

    @POST(API_COLLECT)
    suspend fun collect(@Path("id") id: Int): ObjectResponse<Any>

    @POST(API_UNCOLLECT_ARTICLE)
    suspend fun uncollectFromArticle(@Path("id") id: Int): ObjectResponse<Any>

    @POST(API_UNCOLLECT_MY)
    @FormUrlEncoded
    suspend fun uncollectFromMine(@Path("id") id: Int, @FieldMap params: Map<String, Int>): ObjectResponse<Any>
}