package com.mtu.ceit.hhk.comet.ui.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn

import com.mtu.ceit.hhk.comet.data_models.Movie
import com.mtu.ceit.hhk.comet.data_models.MovieResponse
import com.mtu.ceit.hhk.comet.data_models.TVResponse
import com.mtu.ceit.hhk.comet.network.TMDB_API
import com.mtu.ceit.hhk.comet.repositories.MediaSearchRepository
import com.mtu.ceit.hhk.comet.repositories.movies.MovieRepository
import com.mtu.ceit.hhk.comet.repositories.tv.TVRepository
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val reposMovs:MovieRepository ,
private val reposTVs:TVRepository):ViewModel() {


    var nowPlayingMovs:MutableStateFlow<Resource<MovieResponse>> = MutableStateFlow(Resource.EMPTY)

    var upComingMovs:MutableStateFlow<Resource<MovieResponse>> = MutableStateFlow(Resource.EMPTY)

    var otaTVs:MutableStateFlow<Resource<TVResponse>> = MutableStateFlow(Resource.EMPTY)

    var popularTVs:MutableStateFlow<Resource<TVResponse>> = MutableStateFlow(Resource.EMPTY)

    init {
        getNowMovies()
        getComingMovies()

        getTVOTA()
        getTVPopular()


    }

    private fun getTVPopular() {
        viewModelScope.launch {
            popularTVs.value = Resource.LOADING

            viewModelScope.launch {
                when(val res = reposTVs.getTVPopular()){
                    is Resource.Success -> {
                        popularTVs.value = res
                    }
                    is Resource.ERROR -> {
                        popularTVs.value = res
                    }
                }
            }


        }
    }

    private fun getTVOTA(){
        viewModelScope.launch {
            otaTVs.value = Resource.LOADING

            viewModelScope.launch {
                when(val res = reposTVs.getTVOTA()){
                    is Resource.Success -> {
                        otaTVs.value = res
                    }
                    is Resource.ERROR -> {
                        otaTVs.value = res
                    }
                }
            }


        }
    }

    private fun getComingMovies(){
        viewModelScope.launch {
            upComingMovs.value = (Resource.LOADING)

            when(val resources =  reposMovs.getComingMovies<MovieResponse>()){
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

            when(val resources =  reposMovs.getNowPlaying<MovieResponse>()){
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