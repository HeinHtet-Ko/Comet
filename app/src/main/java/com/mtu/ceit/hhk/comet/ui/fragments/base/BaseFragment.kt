package com.mtu.ceit.hhk.comet.ui.fragments.base
//
//import android.os.Bundle
//import android.renderscript.ScriptGroup
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.ViewModel
//import androidx.paging.PagingData
//import androidx.viewbinding.ViewBinding
//import com.mtu.ceit.hhk.comet.data_models.Movie
//import com.mtu.ceit.hhk.comet.ui.viewmodels.MainViewModel
//import com.mtu.ceit.hhk.comet.ui.viewmodels.MediaSearchViewModel
//
//abstract class BaseFragment():Fragment() {
//
//
// val baseVM:MediaSearchViewModel by viewModels()
//    var basePagers:PagingData<Movie> ?= null
//            override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//
//
//                baseVM.currentQuery.value = "Face"
//
//        baseVM.pagers.observe(viewLifecycleOwner){
//                        basePagers = it
//        }
//        return super.onCreateView(inflater, container, savedInstanceState)
//
//
//    }
//
//
//}