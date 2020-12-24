package com.mtu.ceit.hhk.comet.data_models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReviewResult(
        @Expose
        @SerializedName("results")
        val reviews:List<Review>
)
data class Review(
        @Expose
        val author:String,
        @Expose
        val content:String
)