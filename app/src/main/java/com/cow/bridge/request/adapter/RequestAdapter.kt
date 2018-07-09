package com.cow.bridge.request.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.R
import com.cow.bridge.model.Content
import com.cow.bridge.model.Request
import com.cow.bridge.request.activity.RequestContentActivity
import kotlinx.android.synthetic.main.row_request_simple.view.*

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class RequestAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = ArrayList<Request>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.row_request_simple, parent, false)
        return RequestViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        with((holder as RequestViewHolder).itemView){
            request_layout_main.setOnClickListener{
                var intent = Intent(_context, RequestContentActivity::class.java)
                intent.putExtra("requestContents", items[position])
                (_context as Activity).startActivity(intent)
            }

            request_text_title.text = items[position].iboardTitle
            request_text_name.text = "${items[position].userIdx}"
            request_text_date.text = items[position].iboardDate?.toString()
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun clear(){
        this.items.clear()
    }

    fun addAll(request: java.util.ArrayList<Request>) {
        this.items.addAll(request)
    }


    private inner class RequestViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}