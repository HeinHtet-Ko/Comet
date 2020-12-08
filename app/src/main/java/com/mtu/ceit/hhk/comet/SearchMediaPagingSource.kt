package com.mtu.ceit.hhk.comet

import android.util.Log
import androidx.paging.PagingSource
import com.mtu.ceit.hhk.comet.data_models.Movie
import com.mtu.ceit.hhk.comet.network.TMDB_API
import retrofit2.HttpException
import java.io.IOException

private const val START_PAGE_INDEX = 1

class SearchMediaPagingSource(private val api:TMDB_API,private val query:String): PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val position = params.key ?: START_PAGE_INDEX
        Log.d("flatmap", " main load change ")

        return try {

           val movResponse =   api.searchMovies(query = query,page = position )

            val movList = movResponse.movies
            Log.d("flatmap", "load: ${movList.size.toString()}")

            LoadResult.Page(
                    data = movResponse.movies,
                    prevKey = if(position== START_PAGE_INDEX) null else position-1,
                    nextKey = if(movList.isEmpty()) null else position+1

            )

        }catch (e:IOException){
            Log.d("flatmap", " main load io ")
           LoadResult.Error(e)
        } catch (e:HttpException){
            Log.d("flatmap", " main load http ")
            LoadResult.Error(e)
        }

    }
}