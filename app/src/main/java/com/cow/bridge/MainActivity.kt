package com.cow.bridge

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.cow.bridge.home.activity.HomeFragment
import com.cow.bridge.library.activity.LibraryFragment
import com.cow.bridge.login.activity.LoginActivity
import com.cow.bridge.login.activity.MypageActivity
import com.cow.bridge.request.activity.RequestFragment
import com.cow.bridge.search.activity.SearchActivity
import com.cow.bridge.subscribe.activity.SubscribeFragment
import com.cow.bridge.util.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var prevMenuItem: MenuItem? = null
    var login : Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_button_login.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        main_button_profile.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }

        main_image_search.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        main_bottom_navigation.setOnNavigationItemSelectedListener { item ->
            var sp : SharedPreferences = getSharedPreferences("bridge", MODE_PRIVATE)
            login = sp.getBoolean("login", false)

            when (item.itemId) {

                R.id.action_home -> {
                    main_viewpager.setCurrentItem(0, false)
                    main_toolbar.visibility = VISIBLE
                    main_viewpager.visibility = VISIBLE
                    main_layout_nologin.visibility = GONE
                }
                R.id.action_subscribe -> {
                    main_viewpager.setCurrentItem(1, false)
                    if(login!!){
                        main_viewpager.visibility = VISIBLE
                        main_layout_nologin.visibility = GONE
                        main_toolbar.visibility = VISIBLE
                    }else{
                        main_viewpager.visibility = GONE
                        main_layout_nologin.visibility = VISIBLE
                    }
                }
                R.id.action_request -> {
                    main_viewpager.setCurrentItem(2, false)
                    if(login!!){
                        main_viewpager.visibility = VISIBLE
                        main_layout_nologin.visibility = GONE
                        main_toolbar.visibility = GONE

                    }else{
                        main_viewpager.visibility = GONE
                        main_layout_nologin.visibility = VISIBLE
                    }
                }
                R.id.action_library -> {
                    main_viewpager.setCurrentItem(3, false)
                    if(login!!){
                        main_viewpager.visibility = VISIBLE
                        main_layout_nologin.visibility = GONE
                        main_toolbar.visibility = VISIBLE
                    }else{
                        main_viewpager.visibility = GONE
                        main_layout_nologin.visibility = VISIBLE
                    }
                }
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
        main_viewpager.setSwipeable(false)
    }

    override fun onResume() {
        super.onResume()
        var sp : SharedPreferences = getSharedPreferences("bridge", MODE_PRIVATE)
        login = sp.getBoolean("login", false)
        if(login!!){
            main_button_login.visibility = GONE
            main_button_profile.visibility = VISIBLE
        }else{
            main_button_login.visibility = VISIBLE
            main_button_profile.visibility = GONE
        }

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
