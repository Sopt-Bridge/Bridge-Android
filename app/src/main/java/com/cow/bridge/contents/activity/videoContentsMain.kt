package com.cow.bridge.contents.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Adapter
import com.cow.bridge.MainActivity
import kotlinx.android.synthetic.main.video_contents.*
import com.cow.bridge.R
import com.cow.bridge.contents.adapter.videoContentsTabAdapter

class videoContentsMain :AppCompatActivity(){
    private var tablayout : TabLayout?= null
    var viewPager : ViewPager?= null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.video_contents)

        tablayout = video_contents_tab
        viewPager = video_contents_vp_viewpager
        tablayout!!.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment()
    }

    internal inner class ViewPagerAdapter(manager : FragmentManager) : FragmentPagerAdapter(manager){
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position : Int) : Fragment{
            return mFragmentList[position]
        }
        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }
}
