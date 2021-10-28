package com.mtu.ceit.hhk.comet.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mtu.ceit.hhk.comet.data_models.Review
import com.mtu.ceit.hhk.comet.databinding.ReviewItemLayoutBinding
import kotlin.math.roundToInt

class ReviewAdapter(diff:DiffUtil.ItemCallback<Review>): ListAdapter<Review, ReviewAdapter.ReviewHolder>(diff) {

    inner class ReviewHolder(private val itemBinding:ReviewItemLayoutBinding) :RecyclerView.ViewHolder(itemBinding.root){

        init {
            itemBinding.reviewContentText.setOnClickListener {
                itemBinding.reviewContentText.toggle()
            }
        }
        fun bindViews(review:Review){

            itemBinding.reviewAuthorName.text = (review.author)
            itemBinding.reviewContentText.text= review.content


            if(review.author_details.rating!=null){


                val fr = (review.author_details.rating.toFloat() / 2)

                Log.d("bveog", "bindViews: $fr")
               // Toast.makeText(itemView.context,fr.toString(),Toast.LENGTH_LONG)
                itemBinding.reviewAuthorName.append(review.author_details.rating.toString())
               itemBinding.reviewRating.rating = fr.roundToInt().toFloat()

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