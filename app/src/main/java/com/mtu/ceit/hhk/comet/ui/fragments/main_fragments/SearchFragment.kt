package com.mtu.ceit.hhk.comet.ui.fragments.main_fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.databinding.FragmentSearchBinding
import com.mtu.ceit.hhk.comet.ui.MainActivity
import com.mtu.ceit.hhk.comet.ui.viewmodels.MediaSearchViewModel
import com.mtu.ceit.hhk.comet.ui.adapters.paging_adapter.MediaPagerAdapter
import com.mtu.ceit.hhk.comet.ui.fragments.search_pagers.MovieSearchPager
import com.mtu.ceit.hhk.comet.ui.fragments.search_pagers.TVSearchPager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment:Fragment(R.layout.fragment_search) {


    private  var _binding:FragmentSearchBinding ? = null
    private val binding get() = _binding!!
    private val movFragment = MovieSearchPager()
    private val tvFragment  = TVSearchPager()
    private lateinit var mediaPagerAdapter: MediaPagerAdapter
   //private val searchVM:MediaSearchViewModel by viewModels()
   private lateinit var searchVM: MediaSearchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        _binding = FragmentSearchBinding.bind(view)
        setUpPager()
        listenSearchQuery()

        searchVM = (activity as MainActivity).mainSearchVM

        if(!searchVM.currentQuery.value.isNullOrEmpty())
        {
            binding.mediaSearchView.setQuery(searchVM.currentQuery.value,false)
            binding.mediaSearchView.isIconified = false
            binding.mediaSearchView.clearFocus()
        }



    }


    private fun listenSearchQuery(){

        binding.mediaSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                searchVM.currentQuery.value = query
                binding.mediaSearchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

        })


    }

    private fun setUpPager(){
        mediaPagerAdapter = MediaPagerAdapter(requireActivity().supportFragmentManager,0)
        mediaPagerAdapter.addFragment(movFragment,"Movies")
        mediaPagerAdapter.addFragment(tvFragment,"TV Shows")


        binding.apply {


            mediaViewpager.adapter = mediaPagerAdapter
            mediaSearchTabLayout.setupWithViewPager(mediaViewpager)

        }



    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}