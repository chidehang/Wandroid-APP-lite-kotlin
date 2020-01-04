package com.cdh.wandroid.model

import com.cdh.wandroid.model.bean.HomeArticlesInfo
import com.cdh.wandroid.network.Fetcher
import com.cdh.wandroid.network.RetrofitClient
import com.cdh.wandroid.network.response.ApiResult
import com.cdh.wandroid.network.response.ObjectResponse

/**
 * Created by chidehang on 2020-01-04
 */
class HomeArticlesRepository {

    suspend fun getArticleData(pageNo: Int): ApiResult<ObjectResponse<HomeArticlesInfo>> {
        return Fetcher.fetch {
            RetrofitClient.getApi().getHomeArticles(pageNo.toString())
        }
    }
}