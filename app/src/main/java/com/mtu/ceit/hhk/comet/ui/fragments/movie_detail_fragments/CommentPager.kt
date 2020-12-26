package com.mtu.ceit.hhk.comet.ui.fragments.movie_detail_fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.databinding.FragmentReviewBinding
import com.mtu.ceit.hhk.comet.ui.MovieDetailActivity
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedMovieViewModel
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.flow.collect

class CommentPager:Fragment(R.layout.fragment_review) {

    private var _binding: FragmentReviewBinding?= null
    private val binding get() = _binding!!
    lateinit var detailedViewModel: DetailedMovieViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReviewBinding.bind(view)

        detailedViewModel = (activity as MovieDetailActivity).detailedVM

        lifecycleScope.launchWhenCreated {
            collectReviews()
        }
    }

   suspend fun collectReviews(){
       detailedViewModel.reviewsFlow.collect {
           when(it) {
               is Resource.Success -> {
                   it.value?.let {reviews ->
                       Toast.makeText(requireContext(), "${reviews.reviews.size.toString()}", Toast.LENGTH_LONG).show()
                   }
                   binding.no.text = "No Reviews Yet"

               }
               is Resource.ERROR -> {

               }
           }
       }
   }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}