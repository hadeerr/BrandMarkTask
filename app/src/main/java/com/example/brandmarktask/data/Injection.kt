package com.example.brandmarktask.data

import com.example.brandmarktask.model.MovieRepository

object Injection {

    fun providerRepository(): MovieDataSource {
        return MovieRepository()
    }
}