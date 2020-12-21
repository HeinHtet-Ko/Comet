package com.mtu.ceit.hhk.comet.ui.fragments

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

class MediaPagerAdapter(fm:FragmentManager, behaviour:Int): FragmentStatePagerAdapter(fm,behaviour) {

    private val fragments:MutableList<Fragment> = ArrayList()
    private val fragmentTitles:MutableList<String> = ArrayList()

    fun addFragment(fragment: Fragment,title:String){
        fragments.add(fragment)
        fragmentTitles.add(title)
    }

    override fun getCount(): Int
    =    fragments.size


    override fun getPageTitle(position: Int): CharSequence? = fragmentTitles[position]

    override fun getItem(position: Int): Fragment  =
        fragments[position]

}