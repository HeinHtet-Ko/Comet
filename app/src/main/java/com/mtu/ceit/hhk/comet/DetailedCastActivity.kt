package com.mtu.ceit.hhk.comet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mtu.ceit.hhk.comet.data_models.Credits
import com.mtu.ceit.hhk.comet.data_models.PersonDetail
import com.mtu.ceit.hhk.comet.databinding.ActivityDetailedCastBinding
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedCastViewModel
import com.mtu.ceit.hhk.comet.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.lang.Exception


@AndroidEntryPoint
class DetailedCastActivity : AppCompatActivity() {

    private var _binding:ActivityDetailedCastBinding ?= null
    private val binding:ActivityDetailedCastBinding get() = _binding!!

    private val castVM:DetailedCastViewModel by viewModels()

    private lateinit var person:PersonDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailedCastBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launchWhenCreated {

            castVM.personFlow.collect {
                when(it) {
                    is Resource.Success -> {

                       person = it.value
                        binding.castShimmerLayout.stopShimmer()
                        binding.shimmerCastItem.shimmerCastMain.visibility = View.GONE
                      //  binding.shimmerCastItem.shimmercas.visibility = View.GONE
                        setPersonInfo(person)

                    }
                    else -> {

                    }
                }
            }
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
    private fun setPersonInfo(person:PersonDetail){



       try {
           binding.apply {

               castDetailBiography.setOnClickListener {
                   castDetailBiography.toggle()
               }

               infoLinear.visibility = View.VISIBLE
               castDetailScroll.visibility = View.VISIBLE

               Glide.with(this@DetailedCastActivity)
                       .load("http://image.tmdb.org/t/p/w500${person.profile_path}")
                       .error(R.drawable.error)
                       .transition(DrawableTransitionOptions.withCrossFade())
                       .into(castDetailProfile)

               castDetailName.text = person.name
               castDetailBiography.text = person.biography
               castDetailBirthPlace.append(person.place_of_birth ?: " ")
               castDetailBornDate.append(person.birthday ?: " ")
               if(person.deathday!=null){
                   castDetailBornDate.append("to ${person.deathday}")
               }
               castDetailPopularity.append(person.popularity.toString())

           }

       }catch (e:Exception){
           Log.d("setperson", "setPersonInfo: ${e.message} ")
       }

    }
}