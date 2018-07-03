package com.cow.bridge.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.R
import com.cow.bridge.model.Content
import kotlinx.android.synthetic.main.row_contents_simple.view.*

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class RecommendedAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = ArrayList<Content>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.row_contents_simple, parent, false)
        return RecommendedViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        with((holder as RecommendedViewHolder).itemView){
            contents_text_title.text = items[position].contents_title
            contents_text_count.text = items[position].contents_runtime
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addAll(contents: java.util.ArrayList<Content>) {
        this.items.addAll(contents)
    }

    private inner class RecommendedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}