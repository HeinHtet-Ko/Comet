package com.mtu.ceit.hhk.comet.ui.fragments.search_pagers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.databinding.FragmentTvSearchBinding
import com.mtu.ceit.hhk.comet.ui.MainActivity
import com.mtu.ceit.hhk.comet.ui.viewmodels.MediaSearchViewModel
import com.mtu.ceit.hhk.comet.ui.adapters.paging_adapter.SearchTVPagingAdapter
import com.mtu.ceit.hhk.comet.utils.OnItemClickListener


class TVSearchPager:Fragment(R.layout.fragment_tv_search),OnItemClickListener{



    private var _binding:FragmentTvSearchBinding ?=null
    private val binding get() = _binding!!

    private lateinit var _adapter: SearchTVPagingAdapter

    private val searchVM: MediaSearchViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTvSearchBinding.bind(view)

        //searchVM = (activity as MainActivity).mainSearchVM

        _adapter = SearchTVPagingAdapter(this)

        recyclerSetup()

    }

    fun recyclerSetup(){
        binding.apply {

            searchTvRecycler.adapter = _adapter
            searchTvRecycler.itemAnimator = null
            searchTvRecycler.setHasFixedSize(true)
        }

        searchVM.tvPages.observe(viewLifecycleOwner){

            _adapter.submitData(viewLifecycleOwner.lifecycle,it)

        }

        _adapter.addLoadStateListener {

            if(it.refresh is LoadState.Loading){
                binding.searchTvRecycler.visibility = View.GONE
                binding.wpLdView.visibility = View.VISIBLE
            }else{
                binding.wpLdView.visibility = View.GONE
                binding.searchTvRecycler.visibility = View.VISIBLE

            }



        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(movieID: Int) {
        Toast.makeText(requireContext(), movieID.toString(), Toast.LENGTH_SHORT).show()
    }


}