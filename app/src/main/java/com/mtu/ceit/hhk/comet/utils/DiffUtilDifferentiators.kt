package com.mtu.ceit.hhk.comet.utils

import androidx.recyclerview.widget.DiffUtil
import com.mtu.ceit.hhk.comet.data_models.Cast

import com.mtu.ceit.hhk.comet.data_models.Movie
import com.mtu.ceit.hhk.comet.data_models.TV

class DiffUtilDifferentiators {

    companion object{
         val MovieDifferentiator =  object:DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }



        val CastDifferentiator = object :DiffUtil.ItemCallback<Cast> () {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
               return  oldItem == newItem
            }

        }

        val TVDifferentiator =  object:DiffUtil.ItemCallback<TV>(){
            override fun areItemsTheSame(oldItem: TV, newItem: TV): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TV, newItem: TV): Boolean {
                return oldItem == newItem
            }

        }
    }


}
