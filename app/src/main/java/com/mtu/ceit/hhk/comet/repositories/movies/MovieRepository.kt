package com.mtu.ceit.hhk.comet.repositories.movies

import com.mtu.ceit.hhk.comet.network.TMDB_API
import com.mtu.ceit.hhk.comet.utils.Resource
import java.lang.Exception
import javax.inject.Inject


class MovieRepository @Inject constructor(private val api:TMDB_API) {

    suspend fun<T> getNowPlaying():Resource<T>
    {

       return try {


           Resource.Success(api.getNowMovies() as T)

        }catch (e:Exception){

           Resource.ERROR(e.message!!)
        }
    }

    suspend fun<T> getComingMovies():Resource<T>
    {

        return try {


            Resource.Success(api.getComingMovies() as T)

        }catch (e:Exception){

            Resource.ERROR(e.message!!)
        }
    }

}