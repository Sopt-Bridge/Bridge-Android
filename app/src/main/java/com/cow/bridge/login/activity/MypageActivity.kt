package com.cow.bridge.login.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageButton
import com.cow.bridge.R
import com.cow.bridge.login.adapter.MypageAdapter
import com.cow.bridge.model.MypageItems
import java.util.*


class MypageActivity : AppCompatActivity() {


    lateinit var toolbar: Toolbar
    lateinit var mypageItems : ArrayList<MypageItems>
    lateinit var myAdapter: MypageAdapter
    private lateinit var cancelButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        toolbar = findViewById<View>(R.id.mypage_toolbar) as Toolbar //툴바설정

        val recyclerView = findViewById<View>(R.id.mypage_write) as RecyclerView

        mypageItems = ArrayList()
        mypageItems.add(MypageItems("[Requests] 번역 오역 요청", "2018.01.03"))
        mypageItems.add(MypageItems("[Requests] 영상 문의", "2018.01.03"))
        mypageItems.add(MypageItems("[Requests] 오류 문의", "2018.07.05"))


        myAdapter = MypageAdapter(mypageItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter
        recyclerView.addItemDecoration(ItemDecoration())

        cancelButton = findViewById(R.id.back_btn)
        cancelButton.setOnClickListener{finish()}
    }
}


