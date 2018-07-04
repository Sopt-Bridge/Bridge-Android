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
import java.util.ArrayList


class MypageActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        toolbar = findViewById<View>(R.id.mypage_toolbar) as Toolbar //툴바설정
        toolbar.setTitleTextColor(Color.parseColor("#000000"))
        setSupportActionBar(toolbar)//액션바와 같게 만들어줌

        val recyclerView = findViewById<View>(R.id.mypage_write) as RecyclerView
        val list = ArrayList<String>()
        for (i in 0..19) {
            list.add("Item =$i")
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MypageAdapter(list)


    }

    private inner class MypageAdapter(private val list: List<String>) : RecyclerView.Adapter<MypageViewHolder>() {

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MypageViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(android.R.layout.simple_list_item_1, viewGroup, false)
            return MypageViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: MypageViewHolder, position: Int) {
            val text = list[position]
            viewHolder.title.text = text
        }


        override fun getItemCount(): Int {
            return list.size
        }
    }

    inner class MypageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView

        init {
            title = itemView.findViewById<View>(android.R.id.text1) as TextView


        }

    }
}
