package com.cow.bridge.subscribe.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cow.bridge.R
import com.cow.bridge.model.Hash
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.row_bestchannel_simple.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class MySubscriptionsAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = ArrayList<Hash>()
    val api : ServerInterface? = ApplicationController.instance?.buildServerInterface()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.row_bestchannel_simple, parent, false)
        return MySubscritionsViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        with((holder as MySubscritionsViewHolder).itemView){
            Glide.with(_context).load(items[position].hashImg).into(bestchannel_image_thumbnail)
            bestchannel_text_name.text = items[position].hashName
            bestchannel_text_subscribe_count.text = "Subscribers ${items[position].hashCnt}"
            if(items[position].subflagresult==0){
                bestchannel_image_subscribe.setImageResource(R.drawable.subscribe_normal_btn)
                bestchannel_text_subscribe.setTextColor(Color.parseColor("#D1D1D1"))
            }else{
                bestchannel_image_subscribe.setImageResource(R.drawable.subscribe_active_btn)
                bestchannel_text_subscribe.setTextColor(Color.parseColor("#E31C9E"))
            }

            bestchannel_layout_subscribe.setOnClickListener {
                if(items[position].subflagresult==0){
                    //bestchannel_image_subscribe.init(_context as Activity?)
                    //TODO userIdx 수정
                    var messagesCall = api?.subscribeModify(Hash(items[position].hashName, 1))
                    messagesCall?.enqueue(object : Callback<Network> {
                        override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                            var network = response!!.body()
                            Log.v("subscribeModify : ", Gson().toJson(network))
                            if(network?.message.equals("ok")){
                                items[position].subflagresult = 1
                                bestchannel_image_subscribe.setImageResource(R.drawable.subscribe_active_btn)
                                bestchannel_text_subscribe.setTextColor(Color.parseColor("#E31C9E"))
                                Toast.makeText(_context, "${items[position].hashName} added", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<Network>?, t: Throwable?) {

                        }
                    })
                }else{
                    //bestchannel_image_subscribe.init(_context as Activity?)
                    var messagesCall = api?.subscribeModify(Hash(items[position].hashName, 1))
                    messagesCall?.enqueue(object : Callback<Network> {
                        override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                            var network = response!!.body()
                            Log.v("subscribeModify : ", Gson().toJson(network))
                            if(network?.message.equals("ok")){
                                items[position].subflagresult = 0
                                bestchannel_image_subscribe.setImageResource(R.drawable.subscribe_normal_btn)
                                bestchannel_text_subscribe.setTextColor(Color.parseColor("#D1D1D1"))
                                Toast.makeText(_context, "${items[position].hashName} removed", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<Network>?, t: Throwable?) {

                        }
                    })
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun clear(){
        this.items.clear()
    }

    fun addAll(hashes: java.util.ArrayList<Hash>) {
        this.items.addAll(hashes)
    }


    private inner class MySubscritionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}