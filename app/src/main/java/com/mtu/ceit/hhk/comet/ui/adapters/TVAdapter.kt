package com.mtu.ceit.hhk.comet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.data_models.Movie
import com.mtu.ceit.hhk.comet.data_models.TV
import com.mtu.ceit.hhk.comet.databinding.MovieItemLayoutBinding
import com.mtu.ceit.hhk.comet.utils.OnItemClickListener



class TVAdapter(diffCallback: DiffUtil.ItemCallback<TV>, val listener: OnItemClickListener) : ListAdapter<TV, TVAdapter.TVViewHolder>(diffCallback) {


    inner class TVViewHolder(private val itembinding: MovieItemLayoutBinding) :RecyclerView.ViewHolder(itembinding.root){

        init {
            itembinding.root.setOnClickListener {

                listener.onItemClick(getItem(bindingAdapterPosition).id)
            }
        }
        fun bindViews(tv:TV){

              Glide.with(itemView)
                  .load("http://image.tmdb.org/t/p/w500${tv.poster_path}")
                  .transition(DrawableTransitionOptions.withCrossFade())
                      .error(R.drawable.error)
                      .into(itembinding.moviePhoto)
               itembinding.movieRating.text = tv.vote_average.toString().trim()
               itembinding.movieTitle.text = tv.name.trim()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVViewHolder {
        val itemBinding = MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TVViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: TVViewHolder, position: Int) {

        holder.bindViews(getItem(position))

    }
}
