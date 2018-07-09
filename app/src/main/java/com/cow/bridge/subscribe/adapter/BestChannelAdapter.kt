package com.cow.bridge.subscribe.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.cow.bridge.R
import com.cow.bridge.model.Hash
import kotlinx.android.synthetic.main.row_bestchannel_simple.view.*

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class BestChannelAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = ArrayList<Hash>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.row_bestchannel_simple, parent, false)
        return BestChannelViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        with((holder as BestChannelViewHolder).itemView){
            Glide.with(_context).load("").into(bestchannel_image_thumbnail)
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
                    items[position].subflagresult = 1
                    bestchannel_image_subscribe.setImageResource(R.drawable.subscribe_active_btn)
                    bestchannel_text_subscribe.setTextColor(Color.parseColor("#E31C9E"))
                    //TODO 구독 연동
                }else{
                    items[position].subflagresult = 0
                    bestchannel_image_subscribe.setImageResource(R.drawable.subscribe_normal_btn)
                    bestchannel_text_subscribe.setTextColor(Color.parseColor("#D1D1D1"))
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


    private inner class BestChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}