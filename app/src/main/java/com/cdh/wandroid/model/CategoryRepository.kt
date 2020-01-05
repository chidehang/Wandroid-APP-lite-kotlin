package com.cdh.wandroid.model

import com.cdh.wandroid.model.bean.CategoryBean
import com.cdh.wandroid.network.Fetcher
import com.cdh.wandroid.network.RetrofitClient
import com.cdh.wandroid.network.response.ApiResult
import com.cdh.wandroid.network.response.ArrayResponse

/**
 * Created by chidehang on 2020-01-05
 */
class CategoryRepository {

    suspend fun getCategoryTree(): ApiResult<ArrayResponse<CategoryBean>> {
        return Fetcher.fetch {
            RetrofitClient.getApi().getCategoryTree()
        }
    }
}