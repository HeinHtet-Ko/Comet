package com.mtu.ceit.hhk.comet.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.data_models.Cast

import com.mtu.ceit.hhk.comet.data_models.Movie
import com.mtu.ceit.hhk.comet.databinding.MovieDetailCastItemLayoutBinding
import com.mtu.ceit.hhk.comet.databinding.MovieItemLayoutBinding
import com.mtu.ceit.hhk.comet.utils.OnItemClickListener



class MovieDetailCastAdapter(diffCallback: DiffUtil.ItemCallback<Cast>,val listener: OnItemClickListener)
    : ListAdapter<Cast, MovieDetailCastAdapter.CastViewHolder>(diffCallback) {


    inner class CastViewHolder(private val itembinding: MovieDetailCastItemLayoutBinding) :RecyclerView.ViewHolder(itembinding.root){

        init {
            itembinding.root.setOnClickListener {

                Log.d("RECYCLER", ": ${getItem(bindingAdapterPosition).id}")
                listener.onItemClick(getItem(bindingAdapterPosition).id)
            }
        }
        fun bindViews(cast: Cast){

            Glide.with(itemView)
                .load("http://image.tmdb.org/t/p/w500${cast.profile_path}")
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.error)
                .into(itembinding.movieDetailCastProfile)
           itembinding.movieDetailCastName.text = cast.name
            itembinding.movieDetailCastCharacter.text = "As: ${cast.character}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val itemBinding = MovieDetailCastItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CastViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder:CastViewHolder, position: Int) {

        holder.bindViews(getItem(position))

    }
}
