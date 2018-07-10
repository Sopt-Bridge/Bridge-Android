package com.cow.bridge.subscribe.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.cow.bridge.R
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import com.cow.bridge.subscribe.adapter.BestChannelAdapter
import com.cow.bridge.subscribe.adapter.MySubscriptionsAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_best_channel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BestChannelActivity : AppCompatActivity() {
    var api : ServerInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_best_channel)
        api = ApplicationController.instance?.buildServerInterface()


        val intent = Intent(this.intent)
        val titleStr = intent.getStringExtra("title")

        subscribe_text_title.text = titleStr


        val llm : LinearLayoutManager = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        subscribe_recycler.layoutManager = llm

        if (titleStr.equals("Best Channel")) {
            val bestChannelAdapter = BestChannelAdapter(this)
            subscribe_recycler.adapter = bestChannelAdapter

            var messagesCall = api?.recommendedHashList(0, 1)
            messagesCall?.enqueue(object : Callback<Network> {
                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    Log.v("recommendedHashList : ", Gson().toJson(network))
                    if(network?.message.equals("ok")){
                        network.data?.get(0)?.hashcontents_list?.let {
                            if(it.size!=0){
                                bestChannelAdapter.addAll(it)
                                bestChannelAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<Network>?, t: Throwable?) {

                }
            })

        } else if (titleStr.equals("My Subscriptions")){
            val mySubscriptionsAdapter = MySubscriptionsAdapter(this)
            subscribe_recycler.adapter = mySubscriptionsAdapter

            var messagesCall = api?.getMySubscribeHashList(0, 1)
            messagesCall?.enqueue(object : Callback<Network> {
                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    if(network?.message.equals("ok")){
                        network.data?.get(0)?.hashcontents_list?.let {
                            if(it.size!=0){
                                mySubscriptionsAdapter.addAll(it)
                                mySubscriptionsAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<Network>?, t: Throwable?) {

                }
            })
        }
    }
}
