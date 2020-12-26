package com.mtu.ceit.hhk.comet.ui.fragments.main_fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.databinding.FragmentTvBinding
import com.mtu.ceit.hhk.comet.ui.adapters.NowMovieAdapter
import com.mtu.ceit.hhk.comet.ui.adapters.TVAdapter
import com.mtu.ceit.hhk.comet.ui.viewmodels.MainViewModel
import com.mtu.ceit.hhk.comet.utils.DiffUtilDifferentiators
import com.mtu.ceit.hhk.comet.utils.OnItemClickListener
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.flow.collect


class TVFragment:Fragment(R.layout.fragment_tv) , OnItemClickListener {

    private var _binding:FragmentTvBinding ?= null
    private val binding get() = _binding!!

    private lateinit var _adapterOTA:TVAdapter
    private lateinit var _adapterPopular:TVAdapter

    private val mainVM by navGraphViewModels<MainViewModel>(R.id.bottom_nav){
        defaultViewModelProviderFactory
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentTvBinding.bind(view)

        _adapterOTA = TVAdapter(DiffUtilDifferentiators.TVDifferentiator,this)
        _adapterPopular = TVAdapter(DiffUtilDifferentiators.TVDifferentiator,this)

        recyclerSet()

        collectOTA()
        collectPopular()


    }

     fun collectOTA(){
        lifecycleScope.launchWhenCreated {

            mainVM.otaTVs.collect {
                when(it) {

                    is Resource.Success -> {
                        binding.shimmerMovieList.stopShimmer()
                        binding.otaShimmer.visibility = View.GONE
                        binding.airShimmer.visibility = View.GONE
                        _adapterOTA.submitList(it.value.tvs)
                    }
                    is Resource.ERROR -> {

                    }
                    else -> {

                    }

                }
            }

        }
    }

     fun collectPopular(){
        lifecycleScope.launchWhenCreated {

            mainVM.popularTVs.collect {
                when(it) {

                    is Resource.Success -> {
                        binding.shimmerMovieList.stopShimmer()
                        binding.otaShimmer.visibility = View.GONE
                        binding.airShimmer.visibility = View.GONE
                        _adapterPopular.submitList(it.value.tvs)
                    }
                    is Resource.ERROR -> {

                    }
                    else -> {

                    }

                }
            }

        }
    }

    private fun recyclerSet(){

        binding.otaRecycler.apply {
            setHasFixedSize(true)
            adapter = _adapterOTA
        }
        binding.upRecycler.apply {
            setHasFixedSize(true)
            adapter = _adapterPopular
        }

    }

    override fun onItemClick(itemID: Int) {

    }

}