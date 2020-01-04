package com.cdh.wandroid.network

import java.lang.RuntimeException

/**
 * Created by chidehang on 2020-01-02
 */
data class ApiException(
    val code: Int,
    val msg: String
) : RuntimeException()