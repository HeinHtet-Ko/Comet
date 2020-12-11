package com.mtu.ceit.hhk.comet.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mtu.ceit.hhk.comet.data_models.DetailedMovie
import com.mtu.ceit.hhk.comet.data_models.Movie
import com.mtu.ceit.hhk.comet.repositories.movies.DetailedMovieRepository
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow


class DetailedMovieViewModel @ViewModelInject constructor(
    private val repository: DetailedMovieRepository):ViewModel() {



    val movieFlow:MutableStateFlow<Resource<DetailedMovie>> = MutableStateFlow(Resource.EMPTY)
    init {



    }

    suspend fun fetchDetailedMovie(id:Int){

        movieFlow.value = Resource.LOADING
        when(val resource = repository.getDetailedMovie<DetailedMovie>(id)){
            is Resource.Success -> {
                movieFlow.value = resource
            }
            is Resource.ERROR -> {
                movieFlow.value = Resource.ERROR(resource.message)
            }
        }

    }

}
