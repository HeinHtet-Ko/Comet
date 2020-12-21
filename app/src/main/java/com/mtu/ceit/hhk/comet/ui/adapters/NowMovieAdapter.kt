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
import com.mtu.ceit.hhk.comet.databinding.MovieItemLayoutBinding
import com.mtu.ceit.hhk.comet.utils.OnMovieItemClickListener


class NowMovieAdapter(diffCallback: DiffUtil.ItemCallback<Movie>,val listener: OnMovieItemClickListener) : ListAdapter<Movie, NowMovieAdapter.MovieViewHolder>(diffCallback) {


    inner class MovieViewHolder(private val itembinding: MovieItemLayoutBinding) :RecyclerView.ViewHolder(itembinding.root){

        init {
            itembinding.root.setOnClickListener {

                listener.onMovieItemClick(getItem(bindingAdapterPosition).id)
            }
        }
        fun bindViews(movie:Movie){

              Glide.with(itemView)
                  .load("http://image.tmdb.org/t/p/w500${movie.poster_path}")
                  .transition(DrawableTransitionOptions.withCrossFade())
                      .error(R.drawable.error)
                      .into(itembinding.moviePhoto)
               itembinding.movieRating.text = movie.vote_average.toString().trim()
               itembinding.movieTitle.text = movie.title.trim()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        holder.bindViews(getItem(position))

    }
}
