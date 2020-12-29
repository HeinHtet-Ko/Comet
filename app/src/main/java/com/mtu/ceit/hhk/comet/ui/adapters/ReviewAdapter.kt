package com.mtu.ceit.hhk.comet.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mtu.ceit.hhk.comet.data_models.Review
import com.mtu.ceit.hhk.comet.databinding.ReviewItemLayoutBinding

class ReviewAdapter(diff:DiffUtil.ItemCallback<Review>): ListAdapter<Review, ReviewAdapter.ReviewHolder>(diff) {

    inner class ReviewHolder(val itemBinding:ReviewItemLayoutBinding) :RecyclerView.ViewHolder(itemBinding.root){

        init {
            itemBinding.reviewContentText.setOnClickListener {
                itemBinding.reviewContentText.toggle()
            }
        }
        fun bindViews(review:Review){

            itemBinding.reviewAuthorName.append(review.author)
            itemBinding.reviewContentText.text= review.content


            if(review.author_details.rating!=null){
                val fr = (review.author_details.rating / 2).toFloat()
                itemBinding.reviewAuthorName.append(review.author_details.rating.toString())
               itemBinding.reviewRating.rating = fr
            }else{
                itemBinding.reviewRating.visibility = View.GONE
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {

        val binding = ReviewItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReviewHolder(binding)

    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {

        holder.bindViews(getItem(position))

    }
}