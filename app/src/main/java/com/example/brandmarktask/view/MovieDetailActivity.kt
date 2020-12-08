package com.example.brandmarktask.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.brandmarktask.R
import com.example.brandmarktask.data.Injection
import com.example.brandmarktask.model.MovieDetailModel
import com.example.brandmarktask.view_model.MoviesViewModel
import com.github.fionicholas.movielist.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {
        private lateinit var viewModel: MoviesViewModel

        var  movieId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            setupViewModel()
            movieId = intent.getStringExtra("Movie")!!.toInt()



        }






        private fun setupViewModel() {
            viewModel = ViewModelProvider(this, ViewModelFactory(Injection.providerRepository())).get(
                    MoviesViewModel::class.java
            )
            viewModel.movieDetailModelDetail.observe(this, renderMovies)
            viewModel.isViewLoading.observe(this, isViewLoadingObserver)
            viewModel.onMessageError.observe(this, onMessageErrorObserver)
            viewModel.isEmptyList.observe(this, emptyListObserver)

        }


        private val renderMovies = Observer<List<MovieDetailModel>> {
            Glide.with(this).load(it[0].poster_path).into(movieImg)
            movieName.text = it[0].name
            movieDesc.text = it[0].description
            movieFav.rating = it[0].favorite_count.toFloat()

        }

        private val isViewLoadingObserver = Observer<Boolean> {
            val visibility = if (it) View.VISIBLE else View.GONE
            progress_bar.visibility = visibility
        }

        private val onMessageErrorObserver = Observer<Any> {
            Log.v(MainActivity.tag, "onMessageError $it")
        }

        private val emptyListObserver = Observer<Boolean> {
            Log.v(MainActivity.tag, "emptyListObserver $it")
        }

        override fun onResume() {
            super.onResume()
            viewModel.loadMovieDetail(movieId)
        }
    }