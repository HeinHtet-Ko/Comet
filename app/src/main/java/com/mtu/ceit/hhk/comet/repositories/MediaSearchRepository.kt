package com.mtu.ceit.hhk.comet.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.mtu.ceit.hhk.comet.SearchMediaPagingSource
import com.mtu.ceit.hhk.comet.data_models.Movie
import com.mtu.ceit.hhk.comet.network.TMDB_API
import javax.inject.Inject

class MediaSearchRepository @Inject constructor(
       private val api:TMDB_API
) {



     fun fetchMovieSearch(query: String):LiveData<PagingData<Movie>> {

         Log.d("flatmap", " media repos change ")
     return Pager(

             config = PagingConfig(
                     pageSize = 20,
                     maxSize = 100,
                     enablePlaceholders = false
             ),pagingSourceFactory = {SearchMediaPagingSource(api,query)}
     ).liveData
     }


}