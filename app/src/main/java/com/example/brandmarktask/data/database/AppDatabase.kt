package com.example.brandmarktask.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.brandmarktask.model.Favorite
import com.example.brandmarktask.model.MovieDetailModel

@Database(entities = [MovieDetailModel::class , Favorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao?
    abstract fun favoriteDao(): FavoriteDao?

    companion object {
        private const val DATABASE_NAME = "AppDatabase.db"
        private val TAG = AppDatabase::class.java.simpleName
        private var instance: AppDatabase? = null
        private val LOCK = Any()
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, DATABASE_NAME
                        ).build()
                        Log.i(TAG, "getInstance: New Db instance created")
                    }
                }
            }
            return instance
        }
    }
}