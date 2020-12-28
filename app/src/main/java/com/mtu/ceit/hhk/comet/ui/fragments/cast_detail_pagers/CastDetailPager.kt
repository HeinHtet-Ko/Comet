package com.mtu.ceit.hhk.comet.ui.fragments.cast_detail_pagers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mtu.ceit.hhk.comet.DetailedCastActivity
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.data_models.PersonDetail
import com.mtu.ceit.hhk.comet.databinding.FragmentCastinfoBinding
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedCastViewModel
import com.mtu.ceit.hhk.comet.utils.Resource
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class CastDetailPager :Fragment(R.layout.fragment_castinfo){


    private var _binding:FragmentCastinfoBinding ?= null
    private val binding get() = _binding!!

    private lateinit var castVM:DetailedCastViewModel
    private lateinit var person: PersonDetail


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCastinfoBinding.bind(view)

        castVM = (activity as DetailedCastActivity).castVM


        collectPersonInfo()


    }
    private fun collectPersonInfo(){
        lifecycleScope.launchWhenCreated {

            castVM.personFlow.collect {
                when(it) {
                    is Resource.Success -> {

                        person = it.value
                        setPersonInfo(person)

                    }
                    else -> {

                    }
                }
            }
        }
    }


    private fun setPersonInfo(person: PersonDetail){




            binding.apply {

                castDetailBiography.setOnClickListener {
                    castDetailBiography.toggle()
                }

               castInfoMainRelative.visibility = View.VISIBLE





                castDetailBiography.text = person.biography
                castDetailBirthPlace.append(person.place_of_birth ?: " ")
                castDetailBornDate.append(person.birthday ?: " ")
                if(person.deathday!=null){
                    castDetailBornDate.append("to ${person.deathday}")
                }
                castDetailPopularity.append(person.popularity.toString())

            }



    }




}