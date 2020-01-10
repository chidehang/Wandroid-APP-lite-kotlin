package com.cdh.wandroid.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cdh.wandroid.model.bean.SearchKeyBean

/**
 * Created by chidehang on 2020-01-09
 */
@Dao
interface SearchKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: SearchKeyBean)

    @Query("SELECT * FROM searchkey_table")
    suspend fun getAllSearchKey(): MutableList<SearchKeyBean>

    @Query("DELETE FROM searchkey_table")
    suspend fun deleteAll()
}