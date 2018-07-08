package com.cow.bridge.login.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cow.bridge.R
import com.cow.bridge.model.MypageItems

/**
 * Created by jihaeseong on 2018. 7. 7..
 */

class MypageAdapter(var mypageItems: ArrayList<MypageItems>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_mypage_item, parent, false)
        return MypageViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        with((viewHolder as MypageViewHolder)){
            mypageWriting.text = mypageItems[position].writing
            mypageDate.text = mypageItems[position].date
        }
    }


    override fun getItemCount(): Int {
        return mypageItems.size
    }


    private inner class MypageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mypageWriting : TextView = itemView!!.findViewById<TextView>(R.id.writing)
        var mypageDate : TextView = itemView!!.findViewById<TextView>(R.id.date)

    }
}
