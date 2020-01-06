package com.cdh.wandroid.model

import com.cdh.wandroid.model.bean.ArticlesPageInfo
import com.cdh.wandroid.network.Fetcher
import com.cdh.wandroid.network.RetrofitClient
import com.cdh.wandroid.network.response.ApiResult
import com.cdh.wandroid.network.response.ObjectResponse

/**
 * Created by chidehang on 2020-01-04
 */
class ArticlesRepository {

    suspend fun getHomeArticles(pageNo: Int): ApiResult<ObjectResponse<ArticlesPageInfo>> {
        return Fetcher.fetch {
            RetrofitClient.getApi().getHomeArticles(pageNo.toString())
        }
    }

    suspend fun getCategoryArticles(page: Int, cid: Int): ApiResult<ObjectResponse<ArticlesPageInfo>> {
        return Fetcher.fetch {
            RetrofitClient.getApi().getArticlesByCategory(page, cid)
        }
    }
}