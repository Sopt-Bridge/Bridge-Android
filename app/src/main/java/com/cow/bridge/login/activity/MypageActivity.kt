package com.cow.bridge.login.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cow.bridge.R
import java.util.ArrayList


class MypageActivity : AppCompatActivity() {


    lateinit var toolbar: Toolbar
    lateinit var mypageItems : ArrayList<MypageItems>
    lateinit var myAdapter: MypageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage_activity)
        toolbar = findViewById<View>(R.id.mypage_toolbar) as Toolbar //툴바설정

        val recyclerView = findViewById<View>(R.id.mypage_write) as RecyclerView

        mypageItems = ArrayList()
        mypageItems.add(MypageItems("[Requests] 번역 오역 요청","2018.01.03"))
        mypageItems.add(MypageItems("[Requests] 영상 문의","2018.01.03"))
        mypageItems.add(MypageItems("[Requests] 오류 문의","2018.07.05"))


        myAdapter = MypageAdapter(mypageItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter
        recyclerView.addItemDecoration(ItemDecoration())

    }

    inner class MypageAdapter(var mypageItems: ArrayList<MypageItems>)
        : RecyclerView.Adapter<MypageViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): MypageViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.mypage_item, parent, false)
            return MypageViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: MypageViewHolder, position: Int) {

            viewHolder.mypageWriting.text = mypageItems[position].writing
            viewHolder.mypageDate.text = mypageItems[position].date
        }


        override fun getItemCount(): Int {
            return mypageItems.size
        }
    }

    inner class MypageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mypageWriting : TextView = itemView!!.findViewById<TextView>(R.id.writing)
        var mypageDate : TextView = itemView!!.findViewById<TextView>(R.id.date)

    }
}

