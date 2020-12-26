package com.mtu.ceit.hhk.comet.ui.viewmodels

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtu.ceit.hhk.comet.data_models.CombinedCredits
import com.mtu.ceit.hhk.comet.data_models.PersonDetail
import com.mtu.ceit.hhk.comet.repositories.PersonRepository
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class DetailedCastViewModel @ViewModelInject constructor(
    @Assisted val saveStateHandle:SavedStateHandle , private val repos:PersonRepository
):ViewModel() {

    val personFlow:MutableStateFlow<Resource<PersonDetail>> = MutableStateFlow(Resource.EMPTY)

    val combinedCreditsFlow:MutableStateFlow<Resource<CombinedCredits>> = MutableStateFlow(Resource.EMPTY)

    init {
        val cast_id = saveStateHandle.get<Int>("cast_id")!!


        viewModelScope.launch {
            fetchPerson(cast_id)

            fetchFilmography(cast_id)
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


    private suspend fun fetchFilmography(pID:Int){
        combinedCreditsFlow.value =  Resource.LOADING

        val resource = repos.getPersonFilmography(pID)


        when(resource){
            is Resource.Success -> {
                combinedCreditsFlow.value = resource
            }
            is Resource.ERROR -> {

                combinedCreditsFlow.value = Resource.ERROR(resource.message)
            }
            else -> {

                combinedCreditsFlow.value = Resource.ERROR("Something happened!!")
            }
        }

    }



}