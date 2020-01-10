package com.cdh.wandroid.model.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cdh.wandroid.model.db.ListConverter

/**
 * Created by chidehang on 2020-01-07
 */
@Entity(tableName = "account_table")
@TypeConverters(ListConverter::class)
data class AccountBean(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val collectIds: List<Any>,
    val email: String,
    val icon: String,
    @PrimaryKey val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)