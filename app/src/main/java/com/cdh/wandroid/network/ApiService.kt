package com.cdh.wandroid.network

import com.cdh.wandroid.model.bean.BannerBean
import com.cdh.wandroid.network.response.ArrayResponse
import retrofit2.http.GET

/**
 * Created by chidehang on 2020-01-02
 */
interface ApiService {

    @GET(ApiConstants.API_BANNER)
    suspend fun getBanners(): ArrayResponse<BannerBean>
}