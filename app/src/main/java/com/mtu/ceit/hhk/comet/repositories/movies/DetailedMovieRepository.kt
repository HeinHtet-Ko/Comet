package com.mtu.ceit.hhk.comet.repositories.movies

import com.mtu.ceit.hhk.comet.network.TMDB_API
import com.mtu.ceit.hhk.comet.utils.Resource
import java.lang.Exception
import javax.inject.Inject


class DetailedMovieRepository @Inject constructor(private val api:TMDB_API) {

    suspend fun<T> getDetailedMovie(id:Int):Resource<T>
    {

       return try {

           Resource.Success(api.getDetailedMovie(id) as T)

        }catch (e:Exception){

           Resource.ERROR(e.message!!)
        }
    }


}