package com.cow.bridge.subscribe.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.R
import com.cow.bridge.model.Hash
import com.cow.bridge.subscribe.activity.BestChannelActivity
import kotlinx.android.synthetic.main.row_recommend_subscribe_simple.view.*
import kotlinx.android.synthetic.main.row_subscribe_simple.view.*

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class MySubscribeAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = ArrayList<Hash>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        var convertView : View
        if(viewType==0){
            convertView = LayoutInflater.from(_context).inflate(R.layout.row_subscribe_simple, parent, false)
            return MySubscribeViewHolder(convertView)
        }else{
            convertView = LayoutInflater.from(_context).inflate(R.layout.row_recommend_subscribe_simple, parent, false)
            return RecommendSubscribeViewHolder(convertView)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if(getItemViewType(position)==0){
            with((holder as MySubscribeViewHolder).itemView){

            }
        }else{
            with((holder as RecommendSubscribeViewHolder).itemView){
                recommend_layout_main.setOnClickListener{
                    val intent = Intent(_context, BestChannelActivity::class.java)
                    intent.putExtra("title", "Best Channel")
                    (_context as Activity).startActivity(intent)

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size+1
    }

    fun clear(){
        this.items.clear()
    }

    fun addAll(hashes: java.util.ArrayList<Hash>) {
        this.items.addAll(hashes)
    }
    override fun getItemViewType(position: Int): Int {
        if(position==itemCount-1){
            return 1
        }else{
            return 0
        }
    }

    private inner class MySubscribeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    private inner class RecommendSubscribeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}