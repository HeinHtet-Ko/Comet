package com.mtu.ceit.hhk.comet.ui

import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.*
import com.mtu.ceit.hhk.comet.SearchMediaPagingSource
import com.mtu.ceit.hhk.comet.data_models.Movie
import com.mtu.ceit.hhk.comet.network.TMDB_API
import com.mtu.ceit.hhk.comet.repositories.MediaSearchRepository
import com.mtu.ceit.hhk.comet.repositories.movies.MovieRepository
import kotlinx.coroutines.flow.*


class MediaSearchViewModel @ViewModelInject constructor(private val repos:MediaSearchRepository,private val api:TMDB_API):ViewModel (){

     var currentQuery:MutableLiveData<String> = MutableLiveData("Good")

    var pagers:LiveData<PagingData<Movie>> =  currentQuery.switchMap {
        Pager(

                config = PagingConfig(
                        pageSize = 20,
                        maxSize = 100,
                        enablePlaceholders = false
                ),pagingSourceFactory = { SearchMediaPagingSource(api,it) }
        ).liveData
    }

//    init {
//        search(currentQuery)
//    }

//    fun searchMovie(query:String){
//        currentQuery.value = query
//        pagers = repos.fetchMovieSearch(query).cachedIn(viewModelScope)
//        Log.d("flatmap", "search change ")
//
//
//    }


    fun search(liveStr:LiveData<String>){

    }
}