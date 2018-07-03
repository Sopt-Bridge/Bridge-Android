package com.cow.bridge.home.activity


import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.R
import com.cow.bridge.home.activity.type.HotFragment
import com.cow.bridge.home.activity.type.OtherFragment
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val convertView = inflater!!.inflate(R.layout.fragment_home, container, false)

        val adapter = ViewPagerAdapter(fragmentManager)
        adapter.addFragment(HotFragment(), "HOT")
        adapter.addFragment(OtherFragment.newInstance("k-content"), "K-CONTENT")
        adapter.addFragment(OtherFragment.newInstance("k-pop"), "K-POP")
        adapter.addFragment(OtherFragment.newInstance("fun"), "FUN")
        adapter.addFragment(OtherFragment.newInstance("culture"), "CULTURE")

        with(convertView){
            home_viewpager.adapter = adapter
            home_viewpager.offscreenPageLimit = 1
            home_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {

                }

            })

            home_tabs.tabGravity = TabLayout.GRAVITY_FILL
            home_tabs.tabMode = TabLayout.MODE_SCROLLABLE
            home_tabs.setBackgroundColor(Color.WHITE)
            home_tabs.setTabTextColors(Color.parseColor("#D1D1D1"), Color.parseColor("#E31C9E"))
            home_tabs.setSelectedTabIndicatorColor(Color.parseColor("#E31C9E"))
            home_tabs.setupWithViewPager(home_viewpager)

        }


        return convertView
    }

    inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList.get(position)
        }

        fun addFragment(fragment: Fragment, title : String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

    }

}// Required empty public constructor
