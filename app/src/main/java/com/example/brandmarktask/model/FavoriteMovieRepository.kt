package com.example.brandmarktask.model

import com.example.brandmarktask.data.MovieDataSource
import com.example.brandmarktask.data.OperationCallback

class FavoriteMovieRepository : MovieDataSource {
    override fun retrieveMovie(callback: OperationCallback, page: String) {
        val favoritMovieDetailModels : List<MovieDetailModel> = MovieRepository.myDb?.movieDao()?.getMovies as List<MovieDetailModel>
        if(favoritMovieDetailModels.isNotEmpty()){
            callback.onSuccess(favoritMovieDetailModels)
        }
    }

    override fun retrieveDetailMovie(callback: OperationCallback, id_movie: String) {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }
}