package com.mtu.ceit.hhk.comet.ui.fragments.cast_detail_pagers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mtu.ceit.hhk.comet.ui.DetailedCastActivity
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.databinding.FragmentCastinfoBinding
import com.mtu.ceit.hhk.comet.databinding.FragmentMovieCreditsBinding
import com.mtu.ceit.hhk.comet.databinding.FragmentTvCreditsBinding
import com.mtu.ceit.hhk.comet.ui.MovieDetailActivity
import com.mtu.ceit.hhk.comet.ui.adapters.NowMovieAdapter
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedCastViewModel
import com.mtu.ceit.hhk.comet.utils.DiffUtilDifferentiators
import com.mtu.ceit.hhk.comet.utils.OnItemClickListener
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieCreditPager:Fragment(R.layout.fragment_movie_credits) , OnItemClickListener {

    private var _binding: FragmentMovieCreditsBinding ?= null
    private val binding get() = _binding!!
    private lateinit var castVM:DetailedCastViewModel

    private lateinit var _adapter:NowMovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieCreditsBinding.bind(view)
        _adapter = NowMovieAdapter(DiffUtilDifferentiators.MovieDifferentiator,this)

        castVM = (activity as DetailedCastActivity).castVM

        collectMovieCredits()

    }


    private fun collectMovieCredits(){

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                castVM.movCreditsFlow.collect {
                    when(it) {
                        is Resource.Success -> {
                            recyclerSetUp()
                            _adapter.submitList(it.value.credits)
                            Toast.makeText(requireContext(), "Something happened try again ${it.value.credits.size}", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.ERROR -> {
                            Toast.makeText(requireContext(), "Something happened try again", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
    private fun recyclerSetUp(){

        binding.movieCreditsRecycler.apply {
            adapter = _adapter
            setHasFixedSize(true)
        }

    }

    override fun onItemClick(itemID: Int) {

        Toast.makeText(context,itemID.toString(),Toast.LENGTH_LONG).show()
        val intent = MovieDetailActivity.navigate(requireContext(),itemID)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}