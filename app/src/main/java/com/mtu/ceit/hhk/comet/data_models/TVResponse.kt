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
    val first_air_date: String,
    @Expose
    val poster_path: String,
    @Expose
    val name: String,
    @Expose
    val vote_average: Float


)