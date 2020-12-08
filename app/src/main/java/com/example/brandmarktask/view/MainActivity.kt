package com.example.brandmarktask.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.brandmarktask.R
import com.example.brandmarktask.adapter.MovieListAdapter
import com.example.brandmarktask.data.Injection
import com.example.brandmarktask.data.PaginationScrollListener
import com.example.brandmarktask.model.Movie
import com.example.brandmarktask.view_model.MoviesViewModel
import com.github.fionicholas.movielist.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: MovieListAdapter
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    lateinit  var searchQuery : String
    companion object{
        const val  tag = "My Tag"
        var pageNo : Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MovieListAdapter()
        setupViewModel()
        movies_list.layoutManager = GridLayoutManager(this, 2)
        movies_list.setHasFixedSize(true)
        movies_list.adapter = adapter
        movies_list?.addOnScrollListener(object : PaginationScrollListener(GridLayoutManager(this, 2)) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                //you have to call loadmore items to get more data
                getMoreItems()
            }
        })
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                searchQuery = query
                return true
            }
        })



    }

    fun getMoreItems() {
        isLoading = false
        pageNo++
        viewModel.loadMovies(pageNo)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_list -> {
                //Navigate To Favorite List
                startActivity(Intent(this , FavoriteActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(Injection.providerRepository())).get(
            MoviesViewModel::class.java
        )
        viewModel.movies.observe(this, renderMovies)
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)

    }


    private val renderMovies = Observer<List<Movie>> {
        if(searchQuery.isNotEmpty()) {
            adapter.setMovies(it.filter { it -> it.title == searchQuery }.toList())
        }else {
            adapter.setMovies(it)
        }
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        progress_bar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(tag, "onMessageError $it")
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(tag, "emptyListObserver $it")
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMovies(pageNo)
    }
}