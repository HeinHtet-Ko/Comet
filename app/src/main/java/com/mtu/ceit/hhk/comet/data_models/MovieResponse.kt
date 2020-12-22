package com.mtu.ceit.hhk.comet.data_models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @Expose
    val page :Int,
    @SerializedName("results")
    @Expose
    val movies: List<Movie>
)

data class Movie(
    @Expose
    val id: Int,
    @Expose
    val release_date: String,
    @Expose
    val poster_path: String,
    @Expose
    val title: String,
    @Expose
    val vote_average: Float
)
data class DetailedMovie(
    @Expose
    val id:Int,
    @Expose
    val title:String,
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
    val release_date: String,
    @Expose
    val genres:List<Genre>,
    @Expose
    val revenue:Int,
    @Expose
    val budget:Int,
    @Expose
    val runtime:Int,
    @Expose
    val spoken_languages:List<SpokenLanguage>

)
data class SpokenLanguage(
    @Expose
    val name:String
)

data class Genre
(
        val id:Int,
        val name:String
)
