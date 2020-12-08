package com.mtu.ceit.hhk.comet.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.databinding.FragmentMovieBinding

import com.mtu.ceit.hhk.comet.ui.MainViewModel
import com.mtu.ceit.hhk.comet.ui.NowMovieAdapter

import com.mtu.ceit.hhk.comet.utils.DiffUtilCallBack
import com.mtu.ceit.hhk.comet.utils.OnMovieItemClickListener
import com.mtu.ceit.hhk.comet.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MovieFragment:Fragment(R.layout.fragment_movie),OnMovieItemClickListener {



//    private val mainVM by navGraphViewModels<MainViewModel>(R.id.bottom_nav){
//        defaultViewModelProviderFactory
//    }
private val mainVM by viewModels<MainViewModel>()
    private var _binding:FragmentMovieBinding ? = null
    private val binding get() = _binding!!
    private lateinit var _adapter:NowMovieAdapter
    private lateinit var _upComingAdapter:NowMovieAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {




        _binding = FragmentMovieBinding.bind(view)
        recyclerSetup()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {

            collectupComing()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            collectFlow()
        }
    }

    private fun recyclerSetup(){


        _adapter = NowMovieAdapter(DiffUtilCallBack,this)
        _upComingAdapter = NowMovieAdapter(DiffUtilCallBack,this)
        binding.nowPlayingRecycler.adapter = _adapter
        binding.upComingRecycler.adapter = _upComingAdapter
    }


     private suspend fun collectFlow(){

         mainVM.nowPlayingMovs.collect {
             when(it){
                 is Resource.Success -> {
                     binding.mainPG.visibility = View.GONE
                     _adapter.submitList(it.value.movies)

                 }
                 is Resource.ERROR -> {
                     binding.mainPG.visibility = View.GONE

                 }
                 is Resource.LOADING -> {
                     binding.mainPG.visibility = View.VISIBLE
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
                    binding.mainPG.visibility = View.GONE
                    _upComingAdapter.submitList(it.value.movies)
                    Log.d("Upcoming", "onCreate: ${it.value.movies.size.toString()}")
                    GlobalScope.launch (Dispatchers.Main){
                        Toast.makeText(context, "${it.value.movies.size.toString()}", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.ERROR -> {
                    binding.mainPG.visibility = View.GONE
                    Log.d("WAY", "onCreate: ${it}")
                }
                is Resource.LOADING -> {
                    binding.mainPG.visibility = View.VISIBLE
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

    override fun onMovieItemClick(movieID: Int) {
        Toast.makeText(context,movieID.toString(),Toast.LENGTH_LONG).show()
    }

}