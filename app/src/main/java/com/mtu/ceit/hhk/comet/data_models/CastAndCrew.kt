package com.mtu.ceit.hhk.comet.data_models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Credits(
        @Expose
        val id:Int ,
        @Expose
        @SerializedName("cast")
        val casts: List<Cast> ,
        @Expose
        @SerializedName("crew")
        val crews: List<Crew>

        )

data class Cast(
        @Expose
        val id:Int,
        @Expose
        val name:String,
        @Expose
        val profile_path:String,
        @Expose
        val character:String
)

data class Crew(
        @Expose
        val id:Int,
        @Expose
        val name:String,
        @Expose
        val profile_path:String,
        @Expose
        val job:String
)

data class PersonDetail(
        @Expose
        val name:String,
        @Expose
        val profile_path: String,
        @Expose
        val biography:String?,
        @Expose
        val birthday:String?,
        @Expose
        val deathday:String?,
        @Expose
        val place_of_birth:String?,
        @Expose
        val popularity:Float?
)
data class MovieCredits(
        @Expose
        @SerializedName("cast")
        val credits: List<Movie>
)
data class TVCredits (
        @Expose
        @SerializedName("cast")
        val credits:List<TV>
)
