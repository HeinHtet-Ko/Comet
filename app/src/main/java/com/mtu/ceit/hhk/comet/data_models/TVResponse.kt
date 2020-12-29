package com.mtu.ceit.hhk.comet.data_models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TVResponse(
    @Expose
    val page :Int,
    @SerializedName("results")
    @Expose
    val tvs: List<TV>
)

data class TV(
    @Expose
    val id: Int,
    @Expose
    val poster_path: String,
    @Expose
    val name: String,
    @Expose
    val vote_average: Float

)
data class DetailedTV(
        @Expose
        val id:Int,
        @Expose
        val name:String,
        @Expose
        val overview:String,
        @Expose
        val vote_count:Int,
        @Expose
        val vote_average: Float,
        @Expose
        val poster_path: String,
        @Expose
        val backdrop_path:String,
        @Expose
        val first_air_date: String,
        @Expose
        val genres:List<Genre>,
        @Expose
        val spoken_languages:List<SpokenLanguage>

)