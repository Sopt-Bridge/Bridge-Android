package com.cow.bridge.subscribe.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.cow.bridge.R
import com.cow.bridge.subscribe.adapter.BestChannelAdapter
import com.cow.bridge.subscribe.adapter.MySubscriptionsAdapter
import kotlinx.android.synthetic.main.activity_best_channel.*

class BestChannelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_best_channel)


        val intent = Intent(this.intent)
        val titleStr = intent.getStringExtra("title")

        subscribe_text_title.text = titleStr


        val llm : LinearLayoutManager = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        subscribe_recycler.layoutManager = llm

        if (titleStr.equals("Best Channel")) {
            val bestChannelAdapter = BestChannelAdapter(this)
            subscribe_recycler.adapter = bestChannelAdapter

        } else if (titleStr.equals("My Subscriptions")){
            val mySubscriptionsAdapter = MySubscriptionsAdapter(this)
            subscribe_recycler.adapter = mySubscriptionsAdapter

        }
    }
}
