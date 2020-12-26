package com.mtu.ceit.hhk.comet.repositories.tv

import com.mtu.ceit.hhk.comet.data_models.TVResponse
import com.mtu.ceit.hhk.comet.network.TMDB_API
import com.mtu.ceit.hhk.comet.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class TVRepository @Inject constructor(private val api:TMDB_API) {


    suspend fun getTVOTA():Resource<TVResponse>{
        return try {

            val response = api.getTVOTA()

            Resource.Success(response)

        }catch (e:Exception){

            Resource.ERROR(e.message!!)

        }
    }

    suspend fun getTVPopular():Resource<TVResponse>{
        return try {

            val response = api.getTVPopular()

            Resource.Success(response)

        }catch (e:Exception){

            Resource.ERROR(e.message!!)

        }
    }



}