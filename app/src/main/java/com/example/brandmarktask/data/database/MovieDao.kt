package com.example.brandmarktask.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.brandmarktask.model.MovieDetailModel
import com.example.brandmarktask.model.Movie


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieDetailModel: MovieDetailModel?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie?>?)

    //    @Query("SELECT * FROM popular ORDER BY popularity DESC")
    @get:Query("SELECT * FROM movie")
    val getMovies: LiveData<List<Any?>?>?

    //    @Query("SELECT * FROM popular ORDER BY userRating DESC")
    @get:Query("SELECT * FROM movie")
    val topRatedMovies: LiveData<List<Any?>?>?

    @Query("DELETE FROM movie ")
    fun deleteAllMovies()

    @get:Query("SELECT COUNT(*) FROM movie")
    val movieCount: Int
}