package com.mtu.ceit.hhk.comet.utils

import androidx.recyclerview.widget.DiffUtil
import com.mtu.ceit.hhk.comet.data_models.Movie

object DiffUtilCallBack : DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}