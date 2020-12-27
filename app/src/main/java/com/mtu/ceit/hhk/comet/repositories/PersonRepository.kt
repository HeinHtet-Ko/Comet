package com.mtu.ceit.hhk.comet.repositories

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.mtu.ceit.hhk.comet.data_models.*
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

    suspend fun getMovieCredits(personID:Int):Resource<MovieCredits>{

        return try {
            val credits = api.getMovieCredits(personID)
            Resource.Success(credits)
        }catch (e:Exception){
            Resource.ERROR(e.message!!)
        }

    }

    suspend fun getTVCredits(personID:Int):Resource<TVCredits>{

        return try {
            val credits = api.getTVCredits(personID)
            Resource.Success(credits)
        }catch (e:Exception){
            Resource.ERROR(e.message!!)
        }

    }


}