package com.example.brandmarktask.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brandmarktask.data.MovieDataSource
import com.example.brandmarktask.data.OperationCallback
import com.example.brandmarktask.model.MovieDetailModel
import com.example.brandmarktask.model.Movie

class MoviesViewModel (private val repository: MovieDataSource): ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>().apply { value = emptyList() }
    val movies: LiveData<List<Movie>> = _movies

    private val _movieDetail = MutableLiveData<List<MovieDetailModel>>().apply { value = emptyList() }
    val movieDetailModelDetail: LiveData<List<MovieDetailModel>> = _movieDetail

    private val _isViewLoading= MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError= MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList= MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList


    fun loadMovies( page : Int){
        _isViewLoading.postValue(true)

        repository.retrieveMovie(object: OperationCallback {
            override fun onError(obj: Any?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue( obj)
            }

            override fun onSuccess(obj: Any?) {
                _isViewLoading.postValue(false)

                if(obj!=null && obj is List<*>){
                    if(obj.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        _movies.value= obj as List<Movie>
                    }
                }
            }
        } , page.toString())
    }

    fun  loadMovieDetail(movieId : Int){

        _isViewLoading.postValue(true)
        repository.retrieveDetailMovie(object : OperationCallback{
            override fun onError(obj: Any?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue( obj)
            }

            override fun onSuccess(obj: Any?) {
                _isViewLoading.postValue(false)

                if(obj!=null && obj is List<*>){
                    if(obj.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        _movieDetail.value= obj as List<MovieDetailModel>
                    }
                }
            }
        } , movieId.toString())
    }

}