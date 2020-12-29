package com.mtu.ceit.hhk.comet.repositories.tv

import com.mtu.ceit.hhk.comet.data_models.DetailedTV
import com.mtu.ceit.hhk.comet.network.TMDB_API
import com.mtu.ceit.hhk.comet.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class DetailedTVRepository @Inject constructor(val api:TMDB_API) {

    suspend fun getTVDetailed(tv_id:Int): Resource<DetailedTV> {
        return try {
            val res = api.getDetailedTV(tv_id)

            Resource.Success(res)
        }catch (e: Exception){
            Resource.ERROR(e.message!!)
        }
    }

}