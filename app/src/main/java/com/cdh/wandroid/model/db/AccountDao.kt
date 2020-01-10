package com.cdh.wandroid.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cdh.wandroid.model.bean.AccountBean

/**
 * Created by chidehang on 2020-01-08
 */
@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountBean)

    @Query("SELECT * FROM account_table LIMIT 1")
    suspend fun getAccount(): List<AccountBean>

    @Query("DELETE FROM account_table")
    suspend fun deleteAll()
}