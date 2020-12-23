package com.mtu.ceit.hhk.comet.repositories

import androidx.hilt.lifecycle.ViewModelInject
import com.mtu.ceit.hhk.comet.data_models.PersonDetail
import com.mtu.ceit.hhk.comet.network.TMDB_API
import com.mtu.ceit.hhk.comet.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class PersonRepository @Inject constructor (val api:TMDB_API) {


    suspend fun getPersonDetail(personID:Int):Resource<PersonDetail>{

       return try {
           val personDetail = api.getPersonDetail(personID)
           Resource.Success(personDetail)
       }catch (e:Exception){
           Resource.ERROR(e.message!!)
       }

    }


}