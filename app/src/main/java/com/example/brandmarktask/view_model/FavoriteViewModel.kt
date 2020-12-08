package com.example.brandmarktask.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brandmarktask.data.OperationCallback
import com.example.brandmarktask.model.Favorite
import com.example.brandmarktask.model.FavoriteMovieRepository

class FavoriteViewModel (private val repository: FavoriteMovieRepository): ViewModel() {

    private val _favorites = MutableLiveData<List<Favorite>>().apply { value = emptyList() }
    val favorites: LiveData<List<Favorite>> = _favorites

    private val _isViewLoading= MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError= MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList= MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList


    fun loadFavorites(){
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
                        _favorites.value= obj as List<Favorite>
                    }
                }
            }
        } , "")
    }

}