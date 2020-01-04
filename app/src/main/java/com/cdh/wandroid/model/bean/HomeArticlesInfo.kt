package com.cdh.wandroid.model.bean

/**
 * Created by chidehang on 2020-01-04
 */
data class HomeArticlesInfo(
    val curPage: Int,
    val datas: MutableList<ArticleBean>
)