package com.mtu.ceit.hhk.comet.repositories.movies

import android.util.Log
import com.mtu.ceit.hhk.comet.data_models.ReviewResult
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

    suspend fun <E> getCredits(movId:Int):Resource<E> {

        return try {


            val credits = api.getCastAndCrew(movID = movId)


            Resource.Success(credits as E)


        }catch (e:Exception){


            Resource.ERROR(e.message!!)
        }


    }

    suspend fun getReviews(movId: Int):Resource<ReviewResult>
    {
        return try {
            val results = api.getMovieReviews(movId)
            Resource.Success(results)
        }catch (e:Exception){
            Resource.ERROR(e.message!!)
        }

    }


}