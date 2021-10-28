package com.mtu.ceit.hhk.comet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.ui.viewmodels.MediaSearchViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {



    // val mainSearchVM: MediaSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val controller = findNavController(R.id.fragmentContainerView)
        val bv = findViewById<BottomNavigationView>(R.id.bottom_nv)
        bv.setupWithNavController(controller)


    }

}