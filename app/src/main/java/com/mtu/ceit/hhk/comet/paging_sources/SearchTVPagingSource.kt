package com.mtu.ceit.hhk.comet.paging_sources

import android.util.Log
import androidx.paging.PagingSource
import com.mtu.ceit.hhk.comet.data_models.TV
import com.mtu.ceit.hhk.comet.network.TMDB_API
import retrofit2.HttpException
import java.io.IOException

private const val START_PAGE_INDEX = 1

class SearchTVPagingSource(private val api:TMDB_API,private val query:String): PagingSource<Int, TV>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TV> {

        val position = params.key ?: START_PAGE_INDEX
        Log.d("flatmap", " main load change ")

        return try {

           val tvResponse =   api.searchTVs(query = query,page = position )

            val tvList = tvResponse.tvs

            LoadResult.Page(
                    data = tvList,
                    prevKey = if(position== START_PAGE_INDEX) null else position-1,
                    nextKey = if(tvList.isEmpty()) null else position+1

            )

        }catch (e:IOException){

           LoadResult.Error(e)
        } catch (e:HttpException){

            LoadResult.Error(e)
        }

    }
}