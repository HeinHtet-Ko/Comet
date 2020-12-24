package com.mtu.ceit.hhk.comet.ui.viewmodels

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mtu.ceit.hhk.comet.data_models.Credits
import com.mtu.ceit.hhk.comet.data_models.DetailedMovie
import com.mtu.ceit.hhk.comet.data_models.ReviewResult
import com.mtu.ceit.hhk.comet.repositories.movies.DetailedMovieRepository
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class DetailedMovieViewModel @ViewModelInject constructor(
    private val repository: DetailedMovieRepository,
    @Assisted private val savedStateHandle: SavedStateHandle ):ViewModel() {

    val creditsFlow:MutableStateFlow<Resource<Credits>> = MutableStateFlow(Resource.EMPTY)
    val reviewsFlow:MutableStateFlow<Resource<ReviewResult>> = MutableStateFlow(Resource.EMPTY)
    val movieFlow:MutableStateFlow<Resource<DetailedMovie>> = MutableStateFlow(Resource.EMPTY)
    init {


        val movID = savedStateHandle.get<Int>("movie_id")!!
        viewModelScope.launch {
            fetchDetailedMovie(movID)
            fetchCredits(movID)
            fetchReviews(movID)
        }



    }

    private suspend fun fetchReviews(movID: Int) {

        viewModelScope.launch {

            when( val res = repository.getReviews(movID)) {
                is Resource.Success -> {
                    reviewsFlow.value = res
                }
                is Resource.ERROR -> {

                    reviewsFlow.value = Resource.ERROR(res.message)

                }
                else -> {

                }
            }
        }

    }

    private suspend fun fetchDetailedMovie(movID:Int){

        movieFlow.value = Resource.LOADING
        when(val resource = repository.getDetailedMovie<DetailedMovie>(movID)){
            is Resource.Success -> {
                movieFlow.value = resource
            }
            is Resource.ERROR -> {
                movieFlow.value = Resource.ERROR(resource.message)

            }
        }

    }

    private suspend fun fetchCredits(movID:Int){

        creditsFlow.value = Resource.LOADING

        when(val resource = repository.getCredits<Credits>(movID)){
            is Resource.Success -> {
                creditsFlow.value = resource
            }
            is Resource.ERROR -> {

                creditsFlow.value = Resource.ERROR(resource.message)

            }
            else -> {

            }
        }

    }

}
