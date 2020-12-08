package com.example.brandmarktask.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.brandmarktask.R
import com.example.brandmarktask.adapter.MovieListAdapter
import com.example.brandmarktask.data.Injection
import com.example.brandmarktask.model.Favorite
import com.example.brandmarktask.model.MovieDetailModel
import com.example.brandmarktask.view_model.FavoriteViewModel
import com.github.fionicholas.movielist.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class FavoriteActivity : AppCompatActivity() {
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: MovieListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MovieListAdapter()
        movies_list.layoutManager = GridLayoutManager(this, 2)
        movies_list.setHasFixedSize(true)
        movies_list.adapter = adapter
        setupViewModel()

    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(Injection.providerRepository())).get(
            FavoriteViewModel::class.java
        )
        viewModel.favorites.observe(this, renderMovies)
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)

    }


    private val renderMovies = Observer<List<Favorite>> {
            adapter.setMovies(it as List<MovieDetailModel>)
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
        viewModel.loadFavorites()
    }
}