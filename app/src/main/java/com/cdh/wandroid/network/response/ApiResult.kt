package com.cdh.wandroid.network.response

import com.cdh.wandroid.network.ApiException

/**
 * Created by chidehang on 2020-01-02
 */
class ApiResult<T: BaseResponse> {

    private var isSucceed: Boolean = false
    private var response: T ?= null
    private var error: ApiException = ApiException(UNKNOWN_ERROR_CODE, "unknown error.")

    fun succeed(response: T) {
        this.isSucceed = true
        this.response = response
    }

    fun error(error: ApiException) {
        this.isSucceed = false
        this.error = error
    }

    fun isOk(): Boolean {
        return isSucceed
    }

    fun getResponse(): T? {
        return response
    }

    fun getError(): ApiException {
        return error
    }

    companion object {
        const val SUCCEED_CODE = 0
        const val UNKNOWN_ERROR_CODE = -1
        const val NEED_LOGIN_CODE = -1001
    }
}