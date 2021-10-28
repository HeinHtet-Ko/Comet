package com.mtu.ceit.hhk.comet.ui.fragments.movie_detail_pagers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.data_models.DetailedMovie
import com.mtu.ceit.hhk.comet.databinding.FragmentInfoBinding
import com.mtu.ceit.hhk.comet.ui.MovieDetailActivity
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedMovieViewModel
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class InfoPager:Fragment(R.layout.fragment_info) {


    private var _binding:FragmentInfoBinding ?= null
    private val binding get() = _binding!!
    lateinit var detailedViewModel:DetailedMovieViewModel

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        _binding = FragmentInfoBinding.bind(view)

        binding.apply {
            movieDetailOverview.setOnClickListener {
                movieDetailOverview.toggle()
            }
        }

        detailedViewModel = (activity as MovieDetailActivity).detailedVM


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                collect()
            }

        }
    }



    private suspend fun collect(){

            detailedViewModel.movieFlow.collect {
                when(it) {
                    is Resource.ERROR -> {
                        Toast.makeText(requireContext(), "There was an error! Try Again !", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
                        binding.movieDetailOverviewTitle.visibility = View.VISIBLE
                        binding.movieDetailOverview.text = it.value.overview

                        val detailedMovie = it.value
                        setUpMovieInfo(detailedMovie)


                    }
                    else -> {

                    }
                }
            }

    }

    private fun setUpMovieInfo(detailedMovie: DetailedMovie) {

        binding.apply {

            ratingLinear.visibility = View.VISIBLE

            movieDetailRatingText.append(detailedMovie.vote_average.toString())
            movieDetailReleasedDate.append(detailedMovie.release_date)
            movieDetailRatingCount.append(detailedMovie.vote_count.toString())
            movieDetailBudget.append(detailedMovie.budget.toString() +"$")
            movieDetailRevenue.append(detailedMovie.revenue.toString()+"$")
            movieDetailRunningTime.append(detailedMovie.runtime.toString()+"mins")
            movieDetailLanguage.text = " Spoken Language - ${detailedMovie.spoken_languages[0].name}"

        }

    }


}