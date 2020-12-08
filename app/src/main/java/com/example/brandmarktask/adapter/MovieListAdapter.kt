package com.example.brandmarktask.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.brandmarktask.R
import com.example.brandmarktask.constant.Constant.Companion.BASE_POSTER_URL
import com.example.brandmarktask.model.Favorite
import com.example.brandmarktask.model.MovieRepository
import com.example.brandmarktask.model.Movie
import com.example.brandmarktask.view.MovieDetailActivity
import kotlinx.android.synthetic.main.items_movie.view.*
import java.util.ArrayList

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    private var listMovies = ArrayList<Movie>()

    fun setMovies(tvshows: List<Movie>?) {
        if (tvshows == null) return
        this.listMovies.clear()
        this.listMovies.addAll(tvshows)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.items_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = listMovies[position]
        holder.bind(movies)
    }

    override fun getItemCount(): Int = listMovies.size


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {

                tv_title.text = movie.title
                item_rate.rating = movie.vote_average.toFloat()

                itemView.setOnClickListener {
                    var intent : Intent = Intent(it.context , MovieDetailActivity::class.java)
                    intent.putExtra("Movie" , movie.id.toString())
                    it.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                        .load(BASE_POSTER_URL+ movie.poster_path)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                        .into(img_poster)

                img_fav.setOnClickListener{
                    MovieRepository.myDb?.favoriteDao()?.insertFavorite(movie as Favorite)
                }

                img_fav2.setOnClickListener {
                    MovieRepository.myDb?.favoriteDao()?.removeFavorite(movie as Favorite)
                }

            }
        }
    }
}