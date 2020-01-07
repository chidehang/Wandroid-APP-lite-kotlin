package com.cdh.wandroid.network

import com.cdh.wandroid.network.response.ApiResult
import com.cdh.wandroid.network.response.BaseResponse
import com.cdh.wandroid.util.LogUtils

/**
 * Created by chidehang on 2020-01-02
 */
class Fetcher {

    companion object {
        suspend fun <T: BaseResponse> fetch(block: suspend() -> T): ApiResult<T> {
            var result = ApiResult<T>()
            try {
                var response = block.invoke()
                if (response.errorCode == ApiResult.SUCCEED_CODE) {
                    result.succeed(response)
                } else {
                    result.error(ApiException(response.errorCode, response.errorMsg))
                }
            } catch (e: Throwable) {
                LogUtils.printStackTrace(e)
                result.error(ApiException(ApiResult.UNKNOWN_ERROR_CODE, e.message+""))
            }
            return result
        }
    }
}