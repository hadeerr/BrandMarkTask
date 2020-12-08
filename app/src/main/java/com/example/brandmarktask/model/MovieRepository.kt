package com.example.brandmarktask.model

import android.util.Log
import com.example.brandmarktask.view.MainActivity
import com.example.brandmarktask.constant.Constant.Companion.API_KEY
import com.example.brandmarktask.data.MovieDataSource
import com.example.brandmarktask.data.OperationCallback
import com.example.brandmarktask.data.database.AppDatabase
import com.example.brandmarktask.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieRepository : MovieDataSource {

    private var call: Call<MovieResponse>? = null
    private var call2: Call<Movie>? = null
    companion object {
        val myDb: AppDatabase? = null
    }
    override fun retrieveMovie(callback: OperationCallback, page: String) {
        val moviesList : List<Movie> = myDb?.movieDao()?.getMovies as List<Movie>
        if(moviesList.isEmpty()) {
            call = ApiService.build()?.movies(API_KEY, "en-US", page)
            call?.enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    callback.onError(t.message)
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {

                    response?.body()?.let {
                        if (response.isSuccessful) {
                            Log.v(MainActivity.tag, "data ${it.results}")
                            callback.onSuccess(it.results)
                            myDb?.movieDao()?.insertMovies(it.results)

                        } else {
                            callback.onError("Error")
                        }
                    }
                }
            })
        }else {
            Log.v(MainActivity.tag, "data $moviesList")
            callback.onSuccess(moviesList)
            myDb?.movieDao()?.insertMovies(moviesList)
        }
    }


    override fun retrieveDetailMovie(callback: OperationCallback, id_movie: String) {
        call2 = ApiService.build()?.detailMovies(id_movie, API_KEY, "en-US")
        call2?.enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                response?.body()?.let {
                    if (response.isSuccessful) {
                        Log.v(MainActivity.tag, "data ${it.title}")
                        callback.onSuccess(it)
                    } else {
                        callback.onError("Error")
                    }
                }
            }
        })
    }


    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }

}