package com.mtu.ceit.hhk.comet.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.*

import com.mtu.ceit.hhk.comet.data_models.Movie
import com.mtu.ceit.hhk.comet.network.TMDB_API
import com.mtu.ceit.hhk.comet.repositories.MediaSearchRepository
import com.mtu.ceit.hhk.comet.repositories.movies.MovieRepository
import kotlinx.coroutines.flow.*


class MediaSearchViewModel @ViewModelInject constructor(private val repos:MediaSearchRepository):ViewModel (){

     var currentQuery:MutableLiveData<String> = MutableLiveData()

    var isFirstSearch :MutableLiveData<Boolean> = MutableLiveData(true)


    var moviePages = currentQuery.switchMap {

        repos.fetchMovieSearch(it).cachedIn(viewModelScope)
    }

    var tvPages = currentQuery.switchMap {
        repos.fetchTVSearch(it).cachedIn(viewModelScope)
    }


}