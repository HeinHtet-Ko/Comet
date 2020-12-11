package com.mtu.ceit.hhk.comet.ui.fragments.search_pagers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.paging.LoadState
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.databinding.FragmentMovieSearchBinding
import com.mtu.ceit.hhk.comet.ui.MainActivity
import com.mtu.ceit.hhk.comet.ui.viewmodels.MediaSearchViewModel
import com.mtu.ceit.hhk.comet.ui.SearchMoviePagingAdapter
import com.mtu.ceit.hhk.comet.utils.OnMovieItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchPager:Fragment(R.layout.fragment_movie_search),OnMovieItemClickListener {

    private var _binding:FragmentMovieSearchBinding ?= null
    private val binding: FragmentMovieSearchBinding get() = _binding!!
    private lateinit var _adapter:SearchMoviePagingAdapter


    private lateinit var searchVM: MediaSearchViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMovieSearchBinding.bind(view)
        _adapter = SearchMoviePagingAdapter(this)

        binding.apply {

            searchMovieRecycler.adapter = _adapter

        }

        searchVM = (activity as MainActivity).mainSearchVM

        _adapter.addLoadStateListener {

            if(it.refresh is LoadState.Loading){
                binding.searchMovieRecycler.visibility = View.GONE
                binding.wpLdView.visibility = View.VISIBLE
            }else{
                binding.wpLdView.visibility = View.GONE
                binding.searchMovieRecycler.visibility = View.VISIBLE

            }
        }


    }

    override fun onResume() {
        super.onResume()


        searchVM.moviePages.observe(viewLifecycleOwner){

            _adapter.submitData(viewLifecycleOwner.lifecycle,it)

        }
    }


    override fun onMovieItemClick(movieID: Int) {
        Toast.makeText(requireContext(), movieID.toString()+"pya", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}