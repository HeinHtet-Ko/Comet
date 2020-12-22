package com.mtu.ceit.hhk.comet.ui.fragments.movie_detail_fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.data_models.DetailedMovie
import com.mtu.ceit.hhk.comet.databinding.FragmentInfoBinding
import com.mtu.ceit.hhk.comet.ui.MovieDetailActivity
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedMovieViewModel
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.flow.collect


class InfoPager:Fragment(R.layout.fragment_info) {


    private var _binding:FragmentInfoBinding ?= null
    private val binding get() = _binding!!
    lateinit var detailedViewModel:DetailedMovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        _binding = FragmentInfoBinding.bind(view)

        binding.apply {
            movieDetailOverview.setOnClickListener {
                movieDetailOverview.toggle()
            }
        }

        detailedViewModel = (activity as MovieDetailActivity).detailedVM


        lifecycleScope.launchWhenCreated {
            collect()
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

                      //  binding.te1.append("${it.value.runtime} ${it.value.budget} ${it.value.revenue} ${it.value.release_date}")
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