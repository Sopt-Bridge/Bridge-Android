package com.cow.bridge

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import com.cow.bridge.home.activity.HomeFragment
import com.cow.bridge.library.activity.LibraryFragment
import com.cow.bridge.request.activity.RequestFragment
import com.cow.bridge.subscribe.activity.SubscribeFragment
import com.cow.bridge.util.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var prevMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.action_home -> main_viewpager.setCurrentItem(0, false)
                R.id.action_subscribe -> main_viewpager.setCurrentItem(1, false)
                R.id.action_request -> main_viewpager.setCurrentItem(2, false);
                R.id.action_library -> main_viewpager.setCurrentItem(3, false);
            }
            false
        }

        prevMenuItem = main_bottom_navigation.menu.getItem(0)
        prevMenuItem!!.isChecked = main_bottom_navigation.menu.getItem(0).isChecked

        main_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                prevMenuItem?.let {
                    prevMenuItem?.isChecked = false
                    main_bottom_navigation.menu.getItem(position).isChecked = true
                    prevMenuItem = main_bottom_navigation.menu.getItem(position)

                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        main_viewpager.offscreenPageLimit = 3
        main_viewpager.setOnTouchListener { v, event -> true }
        BottomNavigationViewHelper.removeShiftMode(main_bottom_navigation)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment())
        adapter.addFragment(SubscribeFragment())
        adapter.addFragment(RequestFragment())
        adapter.addFragment(LibraryFragment())
        main_viewpager.adapter = adapter
    }

    inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment) {
            mFragmentList.add(fragment)
        }

    }
}
