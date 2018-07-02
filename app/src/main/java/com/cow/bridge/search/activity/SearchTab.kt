package com.cow.bridge.search.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TabHost
import com.cow.bridge.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_activity.*

class SearchTab : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        host.setup()


        val ts1 = host.newTabSpec("Tab1")
        ts1.setIndicator("basic search")
        ts1.setContent(R.id.search_hashtag)
        host.addTab(ts1)

        val ts2 = host.newTabSpec("Tab2")
        ts2.setIndicator("hashtag search")
        ts2.setContent(R.id.search_basic)
        host.addTab(ts2)

        host.currentTab = 0
    }
}
