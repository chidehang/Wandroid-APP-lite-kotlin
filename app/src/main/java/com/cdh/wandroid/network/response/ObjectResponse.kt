package com.cdh.wandroid.network.response

/**
 * Created by chidehang on 2020-01-02
 */
data class ObjectResponse<T>(
    val data: T
) : BaseResponse()