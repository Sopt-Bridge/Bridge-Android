package com.cow.bridge.home.adapter

import android.content.Context
import android.graphics.Point
import android.support.v7.widget.RecyclerView
import android.view.*
import com.cow.bridge.R
import kotlinx.android.synthetic.main.row_contents_vertical_simple.view.*

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class SearchResultAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.row_contents_search_simple, parent, false)
        return RecentViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        with((holder as RecentViewHolder).itemView){

        }

    }

    override fun getItemCount(): Int {
        return 57
    }


    private inner class RecentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}