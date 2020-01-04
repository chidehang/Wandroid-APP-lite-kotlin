package com.cdh.wandroid.model.bean

/**
 * Created by chidehang on 2020-01-01
 */
data class BannerBean(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)