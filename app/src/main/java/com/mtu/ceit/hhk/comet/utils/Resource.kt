package com.mtu.ceit.hhk.comet.utils

sealed class Resource<out T> {

    object EMPTY:Resource<Nothing>()
    data class Success<out T>(val value:T):Resource<T>()
    data class ERROR(val message:String):Resource<Nothing>()
    object LOADING:Resource<Nothing>()


}



