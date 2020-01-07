package com.cdh.wandroid.model

import com.cdh.wandroid.model.bean.AccountBean
import com.cdh.wandroid.network.Fetcher
import com.cdh.wandroid.network.RetrofitClient
import com.cdh.wandroid.network.response.ApiResult
import com.cdh.wandroid.network.response.ObjectResponse

/**
 * Created by chidehang on 2020-01-07
 */
object AccountRepository {

    private var accountBean: AccountBean ?= null

    suspend fun login(username: String, password: String): ApiResult<ObjectResponse<AccountBean>> {
        var result = Fetcher.fetch {
            val params = mutableMapOf("username" to username, "password" to password)
            RetrofitClient.getApi().login(params)
        }

        if (result.isOk()) {
            accountBean = result.getResponse()?.data
        }

        return result
    }

    suspend fun register(username: String, password: String, repassword: String): ApiResult<ObjectResponse<AccountBean>> {
        return Fetcher.fetch {
            val params = mutableMapOf("username" to username, "password" to password, "repassword" to repassword)
            RetrofitClient.getApi().register(params)
        }
    }

    fun isLoggedIn(): Boolean {
        return accountBean != null
    }

    fun getAccountInfo(): AccountBean? {
        return accountBean
    }
}