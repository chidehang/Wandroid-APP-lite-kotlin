package com.cdh.wandroid.model

import com.cdh.wandroid.WandroidApp
import com.cdh.wandroid.model.bean.ArticlesPageInfo
import com.cdh.wandroid.model.bean.SearchKeyBean
import com.cdh.wandroid.model.db.WandroidRoomDatabase
import com.cdh.wandroid.network.Fetcher
import com.cdh.wandroid.network.RetrofitClient
import com.cdh.wandroid.network.response.ApiResult
import com.cdh.wandroid.network.response.ArrayResponse
import com.cdh.wandroid.network.response.ObjectResponse

/**
 * Created by chidehang on 2020-01-08
 */
class SearchRepository {

    private val searchKeyDao = WandroidRoomDatabase.getDatabase(WandroidApp.applicationContext!!).searchKeyDao()

    suspend fun getHotKeys(): ApiResult<ArrayResponse<SearchKeyBean>> {
        return Fetcher.fetch {
            RetrofitClient.getApi().getHotKeys()
        }
    }

    suspend fun search(page: Int, key: String): ApiResult<ObjectResponse<ArticlesPageInfo>> {
        return Fetcher.fetch {
            var params = mutableMapOf("k" to key)
            RetrofitClient.getApi().search(page, params)
        }
    }

    suspend fun saveSearchKey(bean: SearchKeyBean) {
        searchKeyDao.insert(bean)
    }

    suspend fun deleteAll() {
        searchKeyDao.deleteAll()
    }

    suspend fun getAllSearchKey(): MutableList<SearchKeyBean> {
        return searchKeyDao.getAllSearchKey().asReversed()
    }
}