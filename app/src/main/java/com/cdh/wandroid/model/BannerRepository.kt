package com.cdh.wandroid.model

import com.cdh.wandroid.model.bean.BannerBean
import com.cdh.wandroid.network.Fetcher
import com.cdh.wandroid.network.RetrofitClient
import com.cdh.wandroid.network.response.ApiResult
import com.cdh.wandroid.network.response.ArrayResponse

/**
 * Created by chidehang on 2020-01-01
 */
class BannerRepository {

    suspend fun getBannerData(): ApiResult<ArrayResponse<BannerBean>> {
        return Fetcher.fetch {
            RetrofitClient.getApi().getBanners()
        }
    }
}