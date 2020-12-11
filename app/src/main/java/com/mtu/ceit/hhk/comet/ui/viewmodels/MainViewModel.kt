package com.mtu.ceit.hhk.comet.ui.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mtu.ceit.hhk.comet.BuildConfig
import com.mtu.ceit.hhk.comet.data_models.Movie
import com.mtu.ceit.hhk.comet.data_models.MovieResponse
import com.mtu.ceit.hhk.comet.network.TMDB_API
import com.mtu.ceit.hhk.comet.repositories.MediaSearchRepository
import com.mtu.ceit.hhk.comet.repositories.movies.MovieRepository
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val repos:MovieRepository):ViewModel() {


    var nowPlayingMovs:MutableStateFlow<Resource<MovieResponse>> = MutableStateFlow(Resource.EMPTY)

    var upComingMovs:MutableStateFlow<Resource<MovieResponse>> = MutableStateFlow(Resource.EMPTY)



    init {
        getNowMovies()
        getComingMovies()


    }

    private fun getComingMovies(){
        viewModelScope.launch {
            upComingMovs.value = (Resource.LOADING)

            when(val resources =  repos.getComingMovies<MovieResponse>()){
                is Resource.Success ->{
                    upComingMovs.value = (resources)
                }
                is Resource.ERROR -> {
                    upComingMovs.value = (Resource.ERROR("Network Error"))
                }

            }
        }
        }
    private  fun getNowMovies(){
        viewModelScope.launch {

            nowPlayingMovs.value = (Resource.LOADING)

            when(val resources =  repos.getNowPlaying<MovieResponse>()){
                is Resource.Success ->{
                    nowPlayingMovs.value = (resources)
                }
                is Resource.ERROR -> {
                    nowPlayingMovs.value = (Resource.ERROR("Network Error"))
                }

            }
        }
    }






}