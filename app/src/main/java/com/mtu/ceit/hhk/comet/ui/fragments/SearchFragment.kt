package com.mtu.ceit.hhk.comet.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.databinding.FragmentSearchBinding
import com.mtu.ceit.hhk.comet.ui.MediaSearchViewModel
import com.mtu.ceit.hhk.comet.ui.fragments.search_pagers.MovieSearchPager
import com.mtu.ceit.hhk.comet.ui.fragments.search_pagers.TVSearchPager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment:Fragment(R.layout.fragment_search) {


    private  var _binding:FragmentSearchBinding ? = null
    private val binding get() = _binding!!
    private val movFragment = MovieSearchPager()
    private val tvFragment  = TVSearchPager()
    private lateinit var searchPagerAdapter: SearchPagerAdapter
    private val searchVM:MediaSearchViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        _binding = FragmentSearchBinding.bind(view)
        setUpPager()
        listenSearchQuery()


    }

    private fun listenSearchQuery(){

        binding.mediaSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

        })


    }

    private fun setUpPager(){
        searchPagerAdapter = SearchPagerAdapter(requireActivity().supportFragmentManager,0)
        searchPagerAdapter.addFragment(movFragment,"Movies")
        searchPagerAdapter.addFragment(tvFragment,"TV")


        binding.apply {


            mediaViewpager.adapter = searchPagerAdapter
            mediaTabLayout.setupWithViewPager(mediaViewpager)

        }



    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}