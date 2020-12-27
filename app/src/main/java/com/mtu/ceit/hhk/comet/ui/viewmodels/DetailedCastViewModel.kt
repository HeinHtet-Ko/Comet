package com.mtu.ceit.hhk.comet.ui.viewmodels

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtu.ceit.hhk.comet.data_models.MovieCredits
import com.mtu.ceit.hhk.comet.data_models.PersonDetail
import com.mtu.ceit.hhk.comet.data_models.TVCredits
import com.mtu.ceit.hhk.comet.repositories.PersonRepository
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class DetailedCastViewModel @ViewModelInject constructor(
    @Assisted val saveStateHandle:SavedStateHandle , private val repos:PersonRepository
):ViewModel() {

    val personFlow:MutableStateFlow<Resource<PersonDetail>> = MutableStateFlow(Resource.EMPTY)

    val movCreditsFlow:MutableStateFlow<Resource<MovieCredits>> = MutableStateFlow(Resource.EMPTY)

    val tvCreditsFlow:MutableStateFlow<Resource<TVCredits>> = MutableStateFlow(Resource.EMPTY)

    init {
        val cast_id = saveStateHandle.get<Int>("cast_id")!!


        viewModelScope.launch {
            fetchPerson(cast_id)

            fetchMovieCredits(cast_id)

            fetchTVCredits(cast_id)
        }




    }

    private suspend fun fetchPerson(pID:Int){
        personFlow.value =  Resource.LOADING

        val resource = repos.getPersonDetail(pID)
        Log.d("REGIOR", "fetchPerson: $resource")

            when(resource){
                is Resource.Success -> {
                    personFlow.value = resource
                }
                is Resource.ERROR -> {
                    Log.d("REGIO", "fetchPerson: ${resource.message}")
                    personFlow.value = Resource.ERROR(resource.message)
                }
                else -> {
                    Log.d("REGIO", "fetchPerson: ")
                    personFlow.value = Resource.ERROR("Something happened!!")
                }
            }

    }


    private suspend fun fetchMovieCredits(pID:Int){
        movCreditsFlow.value =  Resource.LOADING

        val resource = repos.getMovieCredits(pID)


        when(resource){
            is Resource.Success -> {
                movCreditsFlow.value = resource
            }
            is Resource.ERROR -> {

                movCreditsFlow.value = Resource.ERROR(resource.message)
            }
            else -> {

                movCreditsFlow.value = Resource.ERROR("Something happened!!")
            }
        }

    }


    private suspend fun fetchTVCredits(pID:Int){
        tvCreditsFlow.value =  Resource.LOADING

        val resource = repos.getTVCredits(pID)


        when(resource){
            is Resource.Success -> {
                tvCreditsFlow.value = resource
            }
            is Resource.ERROR -> {

                tvCreditsFlow.value = Resource.ERROR(resource.message)
            }
            else -> {

                tvCreditsFlow.value = Resource.ERROR("Something happened!!")
            }
        }

    }



}