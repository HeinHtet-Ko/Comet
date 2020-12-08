package com.mtu.ceit.hhk.comet.ui.fragments.search_pagers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.databinding.FragmentMovieSearchBinding
import com.mtu.ceit.hhk.comet.ui.MediaSearchViewModel
import com.mtu.ceit.hhk.comet.ui.SearchMoviePagingAdapter
import com.mtu.ceit.hhk.comet.utils.OnMovieItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchPager:Fragment(R.layout.fragment_movie_search),OnMovieItemClickListener {

    private var _binding:FragmentMovieSearchBinding ?= null
    private val binding: FragmentMovieSearchBinding get() = _binding!!
    private lateinit var _adapter:SearchMoviePagingAdapter


    private val searchVM by viewModels<MediaSearchViewModel>()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _adapter = SearchMoviePagingAdapter(this)

        binding.apply {
            searchMovieRecycler.adapter = _adapter
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