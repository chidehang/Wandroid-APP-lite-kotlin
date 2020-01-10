package com.cdh.wandroid.model

import com.cdh.wandroid.WandroidApp
import com.cdh.wandroid.model.bean.AccountBean
import com.cdh.wandroid.model.db.AccountDao
import com.cdh.wandroid.model.db.WandroidRoomDatabase
import com.cdh.wandroid.network.ApiService
import com.cdh.wandroid.network.Fetcher
import com.cdh.wandroid.network.RetrofitClient
import com.cdh.wandroid.network.response.ApiResult
import com.cdh.wandroid.network.response.ObjectResponse

/**
 * Created by chidehang on 2020-01-07
 */
object AccountRepository {

    @Volatile
    private var accountBean: AccountBean ?= null

    private val accountDao = WandroidRoomDatabase.getDatabase(WandroidApp.applicationContext!!).accountDao()

    suspend fun login(username: String, password: String): ApiResult<ObjectResponse<AccountBean>> {
        var result = Fetcher.fetch {
            val params = mutableMapOf("username" to username, "password" to password)
            RetrofitClient.getApi().login(params)
        }

        if (result.isOk()) {
            accountBean = result.getResponse()?.data
            if (accountBean != null) {
                accountDao.insert(accountBean!!)
            }
        }

        return result
    }

    suspend fun register(username: String, password: String, repassword: String): ApiResult<ObjectResponse<AccountBean>> {
        return Fetcher.fetch {
            val params = mutableMapOf("username" to username, "password" to password, "repassword" to repassword)
            RetrofitClient.getApi().register(params)
        }
    }

    suspend fun getAccountInfo(): AccountBean? {
        if (accountBean == null) {
            var beans = accountDao.getAccount()
            if (beans.isNotEmpty()) {
                accountBean = accountDao.getAccount()[0]
            }
        }
        return accountBean
    }

    fun isLoggedIn(): Boolean {
        return accountBean != null
    }

    suspend fun logout() {
        Fetcher.fetch {
            RetrofitClient.getApi().logout()
        }
        clearLocalAccount()
    }

    suspend fun clearLocalAccount() {
        accountDao.deleteAll()
        accountBean = null
        CookieManager.removeCookie(ApiService.BASE_URL)
    }
}