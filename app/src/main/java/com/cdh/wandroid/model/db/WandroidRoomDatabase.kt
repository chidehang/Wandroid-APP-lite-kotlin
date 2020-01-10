package com.cdh.wandroid.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cdh.wandroid.model.bean.AccountBean
import com.cdh.wandroid.model.bean.SearchKeyBean

/**
 * Created by chidehang on 2020-01-08
 */
@Database(entities = [AccountBean::class, SearchKeyBean::class], version = 2, exportSchema = true)
abstract class WandroidRoomDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun searchKeyDao(): SearchKeyDao

    companion object {

        private const val DATABASE_NAME = "wandroid_database"

        @Volatile
        private var INSTANCE: WandroidRoomDatabase? = null

        fun getDatabase(context: Context): WandroidRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        WandroidRoomDatabase::class.java,
                        DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        val MIGRATION_1_2 = object: Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }
    }
}