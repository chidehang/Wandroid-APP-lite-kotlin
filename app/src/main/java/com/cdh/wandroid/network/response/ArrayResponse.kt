package com.cdh.wandroid.network.response

/**
 * Created by chidehang on 2020-01-02
 */
data class ArrayResponse<T>(
    val data: MutableList<T>
) : BaseResponse()