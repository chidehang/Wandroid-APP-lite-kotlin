package com.cdh.wandroid.model

import com.cdh.wandroid.model.bean.ArticlesPageInfo
import com.cdh.wandroid.network.Fetcher
import com.cdh.wandroid.network.RetrofitClient
import com.cdh.wandroid.network.response.ApiResult
import com.cdh.wandroid.network.response.ObjectResponse

/**
 * Created by chidehang on 2020-01-09
 */
object FavoriteRepository {

    suspend fun getMyFavoriteArticles(page: Int): ApiResult<ObjectResponse<ArticlesPageInfo>> {
        return Fetcher.fetch {
            RetrofitClient.getApi().getMyFavoriteArticles(page)
        }
    }

    suspend fun collect(id: Int): ApiResult<ObjectResponse<Any>> {
        return Fetcher.fetch {
            RetrofitClient.getApi().collect(id)
        }
    }

    suspend fun uncollect(id: Int, originId: Int, fromMine: Boolean): ApiResult<ObjectResponse<Any>> {
        return Fetcher.fetch {
            if (fromMine) {
                var params = mapOf("originId" to originId)
                RetrofitClient.getApi().uncollectFromMine(id, params)
            } else {
                RetrofitClient.getApi().uncollectFromArticle(id)
            }
        }
    }
}