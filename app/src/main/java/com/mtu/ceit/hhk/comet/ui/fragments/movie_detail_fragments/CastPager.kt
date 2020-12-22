package com.mtu.ceit.hhk.comet.ui.fragments.movie_detail_fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.databinding.FragmentCastBinding
import com.mtu.ceit.hhk.comet.ui.MovieDetailActivity
import com.mtu.ceit.hhk.comet.ui.adapters.MovieDetailCastAdapter
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedMovieViewModel
import com.mtu.ceit.hhk.comet.utils.DiffUtilDifferentiators
import com.mtu.ceit.hhk.comet.utils.OnItemClickListener
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class CastPager:Fragment(R.layout.fragment_cast) , OnItemClickListener {


    private lateinit  var movieDetailVM:DetailedMovieViewModel
    private lateinit var _adapter:MovieDetailCastAdapter

    private var _binding:FragmentCastBinding ? = null
    private val binding get() = _binding!!

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCastBinding.bind(view)

        movieDetailVM = (activity as MovieDetailActivity).detailedVM

        castRecyclerSetUp()

        lifecycleScope.launchWhenCreated {
            movieDetailVM.creditsFlow.collect {

                when(it) {

                    is Resource.Success -> {
                        binding.castShimmer.stopShimmer()
                        binding.castShimmer.visibility  = View.GONE
                       _adapter.submitList(it.value.casts)


                    }
                    else -> {

                    }

                }

            }
        }




    }

    override fun onResume() {
        super.onResume()
        binding.castShimmer.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        binding.castShimmer.stopShimmer()
    }
    private fun castRecyclerSetUp(){

        _adapter = MovieDetailCastAdapter(DiffUtilDifferentiators.CastDifferentiator,this)

        binding.apply {


            movieDetailCastRecycler.adapter = _adapter
            movieDetailCastRecycler.setHasFixedSize(true)
        }


    }

    override fun onItemClick(itemID: Int) {

    }


}