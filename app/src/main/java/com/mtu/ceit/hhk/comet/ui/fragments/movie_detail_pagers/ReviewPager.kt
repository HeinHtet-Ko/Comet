package com.mtu.ceit.hhk.comet.ui.fragments.movie_detail_pagers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.databinding.FragmentReviewBinding
import com.mtu.ceit.hhk.comet.ui.MovieDetailActivity
import com.mtu.ceit.hhk.comet.ui.adapters.ReviewAdapter
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedMovieViewModel
import com.mtu.ceit.hhk.comet.utils.DiffUtilDifferentiators
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ReviewPager:Fragment(R.layout.fragment_review) {

    private var _binding: FragmentReviewBinding?= null
    private val binding get() = _binding!!
    @ExperimentalCoroutinesApi
    private lateinit var detailedViewModel: DetailedMovieViewModel

    private lateinit var _adapter:ReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReviewBinding.bind(view)

        detailedViewModel = (activity as MovieDetailActivity).detailedVM
        reviewRecycler()

    }

    @ExperimentalCoroutinesApi
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            collectReviews()
        }
    }

    private fun reviewRecycler(){
        _adapter = ReviewAdapter(DiffUtilDifferentiators.ReviewDifferentiator)

        binding.reviewRecycler.apply {
            setHasFixedSize(true)
            adapter = _adapter
        }
    }

   @ExperimentalCoroutinesApi
   suspend fun collectReviews(){
       detailedViewModel.reviewsFlow.collect {
           when(it) {
               is Resource.Success -> {
                   _adapter.submitList(it.value.reviews)
                   Toast.makeText(requireContext(), "${it.value.reviews.size}", Toast.LENGTH_SHORT).show()
               }
               is Resource.ERROR -> {

               }
               is Resource.EMPTY -> {
                    binding.reviewNoText.visibility = View.VISIBLE
               }
               else -> {

               }
           }
       }
   }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}