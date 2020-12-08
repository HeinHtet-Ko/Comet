package com.mtu.ceit.hhk.comet.repositories.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.mtu.ceit.hhk.comet.BuildConfig
import com.mtu.ceit.hhk.comet.SearchMediaPagingSource
import com.mtu.ceit.hhk.comet.data_models.Movie
import com.mtu.ceit.hhk.comet.data_models.MovieResponse
import com.mtu.ceit.hhk.comet.network.TMDB_API
import com.mtu.ceit.hhk.comet.utils.Resource
import java.lang.Exception
import javax.inject.Inject


class MovieRepository @Inject constructor(private val api:TMDB_API) {

    fun fetchMovieSearch(query: String): LiveData<PagingData<Movie>> {

        Log.d("flatmap", " media repos change ")
        return Pager(

                config = PagingConfig(
                        pageSize = 20,
                        maxSize = 100,
                        enablePlaceholders = false
                ),pagingSourceFactory = { SearchMediaPagingSource(api,query) }
        ).liveData
    }
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