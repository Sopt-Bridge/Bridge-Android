package com.cow.bridge.home.activity.type


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.R
import com.cow.bridge.home.activity.type.hot.NowTrendFragment
import com.cow.bridge.home.adapter.NowTrendAdapter
import com.cow.bridge.home.adapter.RecentAdapter
import com.cow.bridge.home.adapter.RecommendedAdapter
import kotlinx.android.synthetic.main.fragment_hot.view.*


class HotFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val convertView = inflater!!.inflate(R.layout.fragment_hot, container, false)

        val adapter = ViewPagerAdapter(fragmentManager!!)
        adapter.addFragment(NowTrendFragment())
        adapter.addFragment(NowTrendFragment())
        adapter.addFragment(NowTrendFragment())
        adapter.addFragment(NowTrendFragment())

        with(convertView){
            hot_viewpager.adapter = adapter
            hot_indicator.setViewPager(hot_viewpager)

            val nowTrendAdapter : NowTrendAdapter = NowTrendAdapter(context)
            val recommendedAdapter : RecommendedAdapter = RecommendedAdapter(context)
            val recentAdapter : RecentAdapter = RecentAdapter(context)

            val llm : LinearLayoutManager = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.HORIZONTAL
            hot_recycler_trend.layoutManager = llm
            hot_recycler_trend.adapter = nowTrendAdapter
            val llm2 : LinearLayoutManager = LinearLayoutManager(context)
            llm2.orientation = LinearLayoutManager.HORIZONTAL
            hot_recycler_recommended.layoutManager = llm2
            hot_recycler_recommended.adapter = recommendedAdapter
            val llm3 : RecyclerView.LayoutManager = GridLayoutManager(context, 2)
            hot_recycler_recent.layoutManager = llm3
            hot_recycler_recent.adapter = recentAdapter

        }


        return convertView
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

}// Required empty public constructor
