package com.cow.bridge.contents.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class videoContentsTabAdapter (fm : FragmentManager) : FragmentPagerAdapter (fm){
    override fun getItem(position: Int): Fragment {
        return when (position){}

    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence {
        return when (position){
            0 -> "First Tab"
            else-> "Second Tab"
        }
    }
}