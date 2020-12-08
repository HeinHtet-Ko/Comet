package com.mtu.ceit.hhk.comet.repositories

import com.mtu.ceit.hhk.comet.utils.Resource
import java.lang.Exception

class BaseRepository {


    suspend fun<T> safeApiCall(apiCall:suspend ()->T):Resource<T>{


        return try {

            Resource.Success(apiCall.invoke())

        }catch (e:Exception){
            Resource.ERROR(e.message!!)
        }

    }

}