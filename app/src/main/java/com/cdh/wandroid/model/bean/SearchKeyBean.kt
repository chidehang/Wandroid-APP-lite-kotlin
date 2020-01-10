package com.cdh.wandroid.model.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by chidehang on 2020-01-08
 */
@Entity(tableName = "searchkey_table")
data class SearchKeyBean(
    val id: Int = 0,
    val link: String = "",
    @PrimaryKey val name: String,
    val order: Int = 0,
    val visible: Int = 0
)