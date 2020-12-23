package com.mtu.ceit.hhk.comet.ui.fragments.main_fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.databinding.FragmentMovieBinding
import com.mtu.ceit.hhk.comet.ui.MovieDetailActivity

import com.mtu.ceit.hhk.comet.ui.viewmodels.MainViewModel
import com.mtu.ceit.hhk.comet.ui.adapters.NowMovieAdapter
import com.mtu.ceit.hhk.comet.utils.DiffUtilDifferentiators
import com.mtu.ceit.hhk.comet.utils.OnItemClickListener

import com.mtu.ceit.hhk.comet.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MovieFragment:Fragment(R.layout.fragment_movie),OnItemClickListener {



    private val mainVM by navGraphViewModels<MainViewModel>(R.id.bottom_nav){
        defaultViewModelProviderFactory
    }

    private var _binding:FragmentMovieBinding ? = null
    private val binding get() = _binding!!
    private lateinit var _adapter: NowMovieAdapter
    private lateinit var _upComingAdapter: NowMovieAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {




        _binding = FragmentMovieBinding.bind(view)
        recyclerSetup()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {

            collectupComing()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            collectFlow()
        }

        binding.shimmerMovieList.startShimmer()
    }

    private fun recyclerSetup(){


        _adapter = NowMovieAdapter(DiffUtilDifferentiators.MovieDifferentiator,this)
        _upComingAdapter = NowMovieAdapter(DiffUtilDifferentiators.MovieDifferentiator,this)
        binding.nowPlayingRecycler.adapter = _adapter
        binding.upComingRecycler.adapter = _upComingAdapter
    }


     private suspend fun collectFlow(){

         mainVM.nowPlayingMovs.collect {
             when(it){
                 is Resource.Success -> {

                     binding.apply {
                         shimmerMovieList.stopShimmer()
                         shimmerMovieList.visibility = View.GONE
                     }

                     _adapter.submitList(it.value.movies)

                 }
                 is Resource.ERROR -> {


                 }
                 is Resource.LOADING -> {

                 }
                 else -> {

                 }
             }
         }
     }

    private suspend fun collectupComing(){


        mainVM.upComingMovs.collect {
            when(it){
                is Resource.Success -> {

                    binding.apply {
                        shimmerMovieList.stopShimmer()
                        shimmerMovieList.visibility = View.GONE
                    }
                    _upComingAdapter.submitList(it.value.movies)

                }
                is Resource.ERROR -> {

                }
                is Resource.LOADING -> {

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

    override fun onItemClick(movieID: Int) {


        Toast.makeText(requireContext(), movieID.toString(), Toast.LENGTH_SHORT).show()
    //  val options =   ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),binding.nowPlayingRecycler.rootView,"movie")
        val intent = MovieDetailActivity.navigate(requireContext(),movieID)
        startActivity(intent)
    }

}