package com.mtu.ceit.hhk.comet.repositories.movies

import android.util.Log
import com.mtu.ceit.hhk.comet.data_models.DetailedMovie
import com.mtu.ceit.hhk.comet.data_models.ReviewResult
import com.mtu.ceit.hhk.comet.network.TMDB_API
import com.mtu.ceit.hhk.comet.utils.Resource
import java.lang.Exception
import javax.inject.Inject


class DetailedMovieRepository @Inject constructor(private val api:TMDB_API) {

    suspend fun<T> getDetailedMovie(id:Int):Resource<DetailedMovie>
    {

       return try {

           Resource.Success(api.getDetailedMovie(id))

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

    suspend fun<R> getReviews(movId: Int):Resource<R>
    {
        return try {
            val results = api.getMovieReviews(movId)

            if(results.reviews.isNotEmpty()){
                Resource.Success(results as R)
            }else{
                Resource.EMPTY
            }

        }catch (e:Exception){

            Resource.ERROR(e.message!!)
        }

    }


}