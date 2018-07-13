package com.cow.bridge.subscribe.activity


import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cow.bridge.R
import com.cow.bridge.model.Content
import com.cow.bridge.model.Hash
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.subscribe.adapter.MySubscribeAdapter
import com.cow.bridge.subscribe.adapter.SubscribeContentAdapter
import com.cow.bridge.util.UtilController
import com.google.gson.Gson
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import kotlinx.android.synthetic.main.fragment_subscribe.*
import kotlinx.android.synthetic.main.fragment_subscribe.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [SubscribeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubscribeFragment : Fragment() {
    val api = ApplicationController.instance?.buildServerInterface()
    var mylistAdapter : MySubscribeAdapter? = null
    var subscribeContentAdapter : SubscribeContentAdapter? = null
    var powerMenu : PowerMenu? = null
    var hashName : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var convertView = inflater!!.inflate(R.layout.fragment_subscribe, container, false)


        powerMenu = PowerMenu.Builder(context)
                .addItem(PowerMenuItem("Upload date", false))
                .addItem(PowerMenuItem("View count", false))
                .addItem(PowerMenuItem("Rating", false))
                .setDividerHeight(UtilController.convertDpToPixel(1f, context).toInt())
                .setDivider(resources.getDrawable(R.drawable.line_rect_1dp_e4e4e4))
                .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
                .setWith(UtilController.convertDpToPixel(150f, context).toInt())
                .setMenuRadius(16f)
                .setMenuShadow(8f)
                .setTextColor(Color.parseColor("#333333"))
                .setMenuColor(Color.parseColor("#FFFFFF"))
                .setOnMenuItemClickListener(onMenuItemClickListener())
                .build()

        with(convertView){
            subscribe_layout_orderby.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    powerMenu?.showAsDropDown(subscribe_layout_orderby, UtilController.convertDpToPixel(15f, context).toInt(), 0)

                }

            })

            mylistAdapter = MySubscribeAdapter(context)
            subscribeContentAdapter = SubscribeContentAdapter(context)

            val llm : LinearLayoutManager = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.HORIZONTAL
            subscribe_recycler_mylist.layoutManager = llm
            subscribe_recycler_mylist.adapter = mylistAdapter

            mylistAdapter?.setOnMySubscribeItemClickListener(object : MySubscribeAdapter.OnMySubscribeItemClickListener{
                override fun onMySubscribeItemClickListener(hash : Hash) {
                    var hashTmp = Hash(hash.hashName)
                    hashName = hash.hashName
                    hashTmp.pageIdx = 0
                    hashTmp.sortType = 0
                    getHashContentList(hashTmp)
                }

            })

            subscribe_text_more.setOnClickListener{
                val intent = Intent(context, BestChannelActivity::class.java)
                intent.putExtra("title", "My Subscriptions")
                startActivity(intent)

            }

            val llm2 : RecyclerView.LayoutManager = GridLayoutManager(context, 2)
            subscribe_recycler.layoutManager = llm2
            subscribe_recycler.adapter = subscribeContentAdapter


        }

        return convertView
    }

    private fun onMenuItemClickListener() = OnMenuItemClickListener<PowerMenuItem>(){ position: Int, powerMenuItem: PowerMenuItem ->
        if(position==0){
            subscribe_text_orderby.text = "Upload date"
            var hashTmp = Hash(hashName)
            hashTmp.pageIdx = 0
            hashTmp.sortType = 0
            getHashContentList(hashTmp)

        }else if(position==1){
            subscribe_text_orderby.text = "View count"
            var hashTmp = Hash(hashName)
            hashTmp.pageIdx = 0
            hashTmp.sortType = 2
            getHashContentList(hashTmp)
        }else if(position==2){
            subscribe_text_orderby.text = "Rating"
            var hashTmp = Hash(hashName)
            hashTmp.pageIdx = 0
            hashTmp.sortType = 1
            getHashContentList(hashTmp)
        }

        powerMenu?.dismiss()

    }

    override fun onResume() {
        super.onResume()

        var sp : SharedPreferences = activity!!.getSharedPreferences("bridge", AppCompatActivity.MODE_PRIVATE)
        Log.v("test 1", sp.getInt("userIdx", 0).toString())
        var messagesCall = api?.getMySubscribeHashList(0, sp.getInt("userIdx", 0))
        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                Log.v("getMySubscribeHashList", Gson().toJson(network))
                if(network?.message.equals("ok")){
                    network.data?.get(0)?.hashcontents_list?.let {
                        if(it.size!=0){
                            mylistAdapter?.clear()
                            mylistAdapter?.addAll(it)
                            mylistAdapter?.notifyDataSetChanged()
                            var hash = Hash(it[0].hashName)
                            hashName = it[0].hashName
                            hash.pageIdx = 0
                            hash.sortType = 0
                            getHashContentList(hash)
                        }else{

                            val llm2 : RecyclerView.LayoutManager = GridLayoutManager(context, 1)
                            subscribe_recycler.layoutManager = llm2
                            subscribe_recycler.adapter = subscribeContentAdapter

                            subscribeContentAdapter?.clear()
                            var noresult = ArrayList<Content>()
                            noresult.add(Content())
                            subscribeContentAdapter?.setEmpty(true)
                            subscribeContentAdapter?.addAll(noresult)
                            subscribeContentAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Network>?, t: Throwable?) {

            }
        })

    }

    fun getHashContentList(hash : Hash){
        hashName = hash.hashName
        Log.v("getHashContentList", Gson().toJson(hash))
        var messagesCall = api?.getHashContentList(hash)
        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                Log.v("getHashContentList : ", Gson().toJson(network))
                if(network?.message.equals("ok")){
                    network.data?.get(0)?.contents_list?.let {
                        if(it.size!=0){

                            val llm2 : RecyclerView.LayoutManager = GridLayoutManager(context, 2)
                            subscribe_recycler.layoutManager = llm2
                            subscribe_recycler.adapter = subscribeContentAdapter

                            subscribeContentAdapter?.clear()
                            subscribeContentAdapter?.setEmpty(false)
                            subscribeContentAdapter?.addAll(it)
                            subscribeContentAdapter?.notifyDataSetChanged()
                        }else{

                            val llm2 : RecyclerView.LayoutManager = GridLayoutManager(context, 1)
                            subscribe_recycler.layoutManager = llm2
                            subscribe_recycler.adapter = subscribeContentAdapter

                            subscribeContentAdapter?.clear()
                            var noresult = ArrayList<Content>()
                            noresult.add(Content())
                            subscribeContentAdapter?.setEmpty(true)
                            subscribeContentAdapter?.addAll(noresult)
                            subscribeContentAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Network>?, t: Throwable?) {

            }
        })
    }
}// Required empty public constructor
