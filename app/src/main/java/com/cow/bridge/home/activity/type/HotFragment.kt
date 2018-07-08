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
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import kotlinx.android.synthetic.main.fragment_hot.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HotFragment : Fragment() {

    var api : ServerInterface? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val convertView = inflater!!.inflate(R.layout.fragment_hot, container, false)
        api = ApplicationController.instance?.buildServerInterface()

        val adapter = ViewPagerAdapter(fragmentManager!!)
        adapter.addFragment(NowTrendFragment())
        adapter.addFragment(NowTrendFragment())
        adapter.addFragment(NowTrendFragment())
        adapter.addFragment(NowTrendFragment())

        with(convertView){
            hot_viewpager.offscreenPageLimit = 3
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

            var messagesCall = api?.nowTrendContentsList(0)
            messagesCall?.enqueue(object : Callback<Network>{
                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    if(network?.message.equals("ok")){
                        network.data?.get(0)?.contents_list?.let {
                            if(it.size!=0){
                                nowTrendAdapter.addAll(it)
                                nowTrendAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<Network>?, t: Throwable?) {

                }
            })

            messagesCall = api?.recommendedContentsList()
            messagesCall?.enqueue(object : Callback<Network>{
                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    if(network?.message.equals("ok")){
                        network.data?.get(0)?.contents_list?.let {
                            if(it.size!=0){
                                recommendedAdapter.addAll(it)
                                recommendedAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<Network>?, t: Throwable?) {

                }
            })

            messagesCall = api?.recentContentsList(0, 0)
            messagesCall?.enqueue(object : Callback<Network>{
                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    if(network?.message.equals("ok")){
                        network.data?.get(0)?.contents_list?.let {
                            if(it.size!=0){
                                recentAdapter.addAll(it)
                                recentAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<Network>?, t: Throwable?) {

                }
            })
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
