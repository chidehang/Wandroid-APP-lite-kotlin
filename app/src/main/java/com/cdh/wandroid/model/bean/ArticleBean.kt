package com.cdh.wandroid.model.bean

/**
 * Created by chidehang on 2020-01-04
 */
data class ArticleBean(
    val viewType: Int = 0,
    val apkLink: String = "",
    val audit: Int = -1,
    val author: String = "",
    val chapterId: Int = -1,
    val chapterName: String = "",
    val collect: Boolean = false,
    val courseId: Int = -1,
    val desc: String = "",
    val envelopePic: String = "",
    val fresh: Boolean = false,
    val id: Int = -1,
    val link: String = "",
    val niceDate: String = "",
    val niceShareDate: String = "",
    val origin: String = "",
    val originId: Int = -1,
    val prefix: String = "",
    val projectLink: String = "",
    val publishTime: Long = 0,
    val selfVisible: Int = -1,
    val shareDate: Long = 0,
    val shareUser: String = "",
    val superChapterId: Int = -1,
    val superChapterName: String = "",
    val tags: List<Tag> = emptyList(),
    val title: String = "",
    val type: Int = -1,
    val userId: Int = -1,
    val visible: Int = 0,
    val zan: Int = 0
) {
    var headerBanners: MutableList<BannerBean> ?= null
}

data class Tag(
    val name: String,
    val url: String
)