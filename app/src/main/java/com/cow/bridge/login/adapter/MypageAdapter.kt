package com.cow.bridge.login.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cow.bridge.R
import com.cow.bridge.model.MypageItems
import com.cow.bridge.model.Request
import com.cow.bridge.request.activity.RequestContentActivity
import com.cow.bridge.util.UtilController
import kotlinx.android.synthetic.main.row_mypage_item.view.*

/**
 * Created by jihaeseong on 2018. 7. 7..
 */

class MypageAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = ArrayList<Request>()

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_mypage_item, parent, false)
        return MypageViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        with(((viewHolder as MypageViewHolder).itemView)){
            mywritten_text_title.text = items[position].iboardTitle
            mywritten_text_date.text = UtilController.timeformat(items[position].iboardDate)

            mywritten_layout_main.setOnClickListener {
                var intent = Intent(_context, RequestContentActivity::class.java)
                intent.putExtra("request", items[position])
                (_context as Activity).startActivity(intent)
            }
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    fun clear(){
        this.items.clear()
    }

    fun addAll(requests: java.util.ArrayList<Request>) {
        this.items.addAll(requests)
    }


    private inner class MypageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
