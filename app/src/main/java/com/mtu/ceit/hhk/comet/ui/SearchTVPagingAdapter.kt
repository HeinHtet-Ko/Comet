package com.mtu.ceit.hhk.comet.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mtu.ceit.hhk.comet.data_models.TV
import com.mtu.ceit.hhk.comet.databinding.MovieItemLayoutBinding
import com.mtu.ceit.hhk.comet.utils.DiffUtilDifferentiators
import com.mtu.ceit.hhk.comet.utils.OnMovieItemClickListener

class SearchTVPagingAdapter(val listener:OnMovieItemClickListener): PagingDataAdapter<TV, SearchTVPagingAdapter.SearchTVViewHolder>(DiffUtilDifferentiators.TVDifferentiator) {


    inner class SearchTVViewHolder(private val itemLayoutBinding: MovieItemLayoutBinding):RecyclerView.ViewHolder(itemLayoutBinding.root){
        init {
            itemLayoutBinding.root.setOnClickListener {

                listener.onMovieItemClick(getItem(bindingAdapterPosition)!!.id)
            }
        }
        fun bindViews(tv: TV){

            Glide.with(itemView)
                    .load("http://image.tmdb.org/t/p/w500${tv.poster_path}")
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemLayoutBinding.moviePhoto)
            itemLayoutBinding.movieRating.text = tv.vote_average.toString().trim()
            itemLayoutBinding.movieTitle.text =tv.name.trim()


        }

    }

    override fun onBindViewHolder(holder: SearchTVViewHolder, position: Int) {

        holder.bindViews(getItem(position)!!)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTVViewHolder {

        val itemBinding = MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchTVViewHolder(itemBinding)
    }
}