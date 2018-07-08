package com.cow.bridge.subscribe.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.R

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class MySubscriptionsAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.row_bestchannel_simple, parent, false)
        return MySubscritionsViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        with((holder as MySubscritionsViewHolder).itemView){

        }

    }

    override fun getItemCount(): Int {
        return 57
    }


    private inner class MySubscritionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}