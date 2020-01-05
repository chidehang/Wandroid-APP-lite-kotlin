package com.cdh.wandroid.model.bean

/**
 * Created by chidehang on 2020-01-05
 */
data class CategoryBean(
    val children: MutableList<CategoryBean>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)