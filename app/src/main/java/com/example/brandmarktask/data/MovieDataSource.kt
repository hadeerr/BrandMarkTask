package com.example.brandmarktask.data

interface MovieDataSource {

    fun retrieveMovie(callback: OperationCallback , page  : String)
    fun retrieveDetailMovie(callback: OperationCallback, id_movie: String)
    fun cancel()

}