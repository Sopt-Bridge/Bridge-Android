package com.cow.bridge.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.R

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class RecommendedAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.row_contents_simple, parent, false)
        return NowTrendViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {



    }

    override fun getItemCount(): Int {
        return 4
    }


    private inner class NowTrendViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}