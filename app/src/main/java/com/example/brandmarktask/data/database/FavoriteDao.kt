package com.example.brandmarktask.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.brandmarktask.model.Favorite


@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(movie: Favorite?)

    @Query("SELECT COUNT() FROM favorites WHERE id = :movieId")
    fun isFavorite(movieId: Int): LiveData<Int?>?

    @get:Query("SELECT * FROM favorites")
    val allFavorites: LiveData<List<Any?>?>?

    @Delete
    fun removeFavorite(movie: Favorite?)
}