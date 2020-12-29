package com.mtu.ceit.hhk.comet.ui.fragments.cast_detail_pagers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mtu.ceit.hhk.comet.ui.DetailedCastActivity
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.databinding.FragmentCastinfoBinding
import com.mtu.ceit.hhk.comet.databinding.FragmentTvCreditsBinding
import com.mtu.ceit.hhk.comet.ui.adapters.TVAdapter
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedCastViewModel
import com.mtu.ceit.hhk.comet.utils.DiffUtilDifferentiators
import com.mtu.ceit.hhk.comet.utils.OnItemClickListener
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.flow.collect

class TVCreditPager:Fragment(R.layout.fragment_tv_credits) , OnItemClickListener {

    private var _binding: FragmentTvCreditsBinding?= null
    private val binding get() = _binding!!
    private lateinit var castVM:DetailedCastViewModel

    private lateinit var _adapter:TVAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTvCreditsBinding.bind(view)
        _adapter = TVAdapter(DiffUtilDifferentiators.TVDifferentiator,this)

        castVM = (activity as DetailedCastActivity).castVM

        recyclerSetUp()
        collectTVCredits()

    }
    private fun collectTVCredits(){
        lifecycleScope.launchWhenCreated {
            castVM.tvCreditsFlow.collect {
                        when(it) {
                            is Resource.Success -> {
                                _adapter.submitList(it.value.credits)
                            }
                            is Resource.ERROR -> {
                                Toast.makeText(requireContext(), "Something happened try again", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }
    }
    private fun recyclerSetUp(){

        binding.tvCreditRecycler.apply {
            adapter = _adapter
            setHasFixedSize(true)
        }

    }

    override fun onItemClick(itemID: Int) {

    }

}