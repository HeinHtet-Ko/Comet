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