package com.mtu.ceit.hhk.comet.ui.adapters.paging_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mtu.ceit.hhk.comet.data_models.Movie
import com.mtu.ceit.hhk.comet.databinding.MovieItemLayoutBinding
import com.mtu.ceit.hhk.comet.utils.DiffUtilDifferentiators
import com.mtu.ceit.hhk.comet.utils.OnItemClickListener


class SearchMoviePagingAdapter(val listener:OnItemClickListener): PagingDataAdapter<Movie, SearchMoviePagingAdapter.SearchMovieViewHolder>(DiffUtilDifferentiators.MovieDifferentiator) {


    inner class SearchMovieViewHolder(val itemLayoutBinding: MovieItemLayoutBinding):RecyclerView.ViewHolder(itemLayoutBinding.root){
        init {
            itemLayoutBinding.root.setOnClickListener {

                listener.onItemClick(getItem(bindingAdapterPosition)!!.id)
            }
        }
        fun bindViews(movie:Movie){

            Glide.with(itemView)
                    .load("http://image.tmdb.org/t/p/w500${movie.poster_path}")
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemLayoutBinding.moviePhoto)
            itemLayoutBinding.movieRating.text = movie.vote_average.toString().trim()
            itemLayoutBinding.movieTitle.text = movie.title.trim()


        }

    }

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {

        holder.bindViews(getItem(position)!!)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {

        val itemBinding = MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchMovieViewHolder(itemBinding)
    }
}