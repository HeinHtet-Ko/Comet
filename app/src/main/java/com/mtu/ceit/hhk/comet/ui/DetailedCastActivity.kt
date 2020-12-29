package com.mtu.ceit.hhk.comet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.data_models.PersonDetail
import com.mtu.ceit.hhk.comet.databinding.ActivityDetailedCastBinding
import com.mtu.ceit.hhk.comet.ui.adapters.NowMovieAdapter
import com.mtu.ceit.hhk.comet.ui.adapters.TVAdapter
import com.mtu.ceit.hhk.comet.ui.adapters.paging_adapter.MediaPagerAdapter
import com.mtu.ceit.hhk.comet.ui.fragments.cast_detail_pagers.CastDetailPager
import com.mtu.ceit.hhk.comet.ui.fragments.cast_detail_pagers.MovieCreditPager
import com.mtu.ceit.hhk.comet.ui.fragments.cast_detail_pagers.TVCreditPager
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedCastViewModel
import com.mtu.ceit.hhk.comet.utils.OnItemClickListener
import com.mtu.ceit.hhk.comet.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class DetailedCastActivity : AppCompatActivity() , OnItemClickListener {

    private var _binding:ActivityDetailedCastBinding ?= null
    private val binding:ActivityDetailedCastBinding get() = _binding!!

    lateinit var mediaPagerAdapter: MediaPagerAdapter

     val castVM:DetailedCastViewModel by viewModels()

    lateinit var person:PersonDetail

    private lateinit var _adapterMov:NowMovieAdapter
    private lateinit var _adapterTv:TVAdapter

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailedCastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpPager()

collectPersonInfo()
    }

    private fun collectPersonInfo(){
        lifecycleScope.launchWhenCreated {

            castVM.personFlow.collect {
                when(it) {
                    is Resource.Success -> {

                        person = it.value
                       binding.castShimmerLayout.stopShimmer()
                       binding.shimmerCastItem.shimmerCastMain.visibility = View.GONE

                        setPersonProfile(person)

                    }
                    else -> {

                    }
                }
            }
        }
    }
private fun setPersonProfile(personDetail: PersonDetail){
    Glide.with(this)
            .load("http://image.tmdb.org/t/p/w500${personDetail.profile_path}")
            .error(R.drawable.error)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.castDetailProfile)
    binding.castDetailName.text = personDetail.name
}

    private fun setUpPager(){
        mediaPagerAdapter = MediaPagerAdapter(supportFragmentManager,0)
        mediaPagerAdapter.addFragment(CastDetailPager()," Info ")
        mediaPagerAdapter.addFragment(MovieCreditPager()," Movies")
        mediaPagerAdapter.addFragment(TVCreditPager(), " TV Shows ")


        binding.apply {

            castDetailViewpager.adapter = mediaPagerAdapter
            castDetailTablayout.setupWithViewPager(castDetailViewpager)

        }

    }

    override fun onResume() {
        super.onResume()
        binding.castShimmerLayout.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        binding.castShimmerLayout.stopShimmer()
    }


    override fun onItemClick(itemID: Int) {

    }
}