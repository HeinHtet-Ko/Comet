package com.mtu.ceit.hhk.comet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mtu.ceit.hhk.comet.databinding.ActivityDetailedCastBinding
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedCastViewModel
import com.mtu.ceit.hhk.comet.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class DetailedCastActivity : AppCompatActivity() {

    private var _binding:ActivityDetailedCastBinding ?= null
    private val binding:ActivityDetailedCastBinding get() = _binding!!

    private val castVM:DetailedCastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailedCastBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launchWhenCreated {

            castVM.personFlow.collect {
                when(it) {
                    is Resource.Success -> {

                        Toast.makeText(applicationContext, it.value.name, Toast.LENGTH_SHORT).show()
                        binding.all.append(it.value.biography)
                        binding.all.append(it.value.birthday)
                       // binding.all.append(it.value.deathday)
                        binding.all.append(it.value.name)
                        binding.all.append(it.value.place_of_birth)
                        binding.all.append(it.value.profile_path)
                    }
                    else -> {

                    }
                }
            }
        }


    }
}