package com.cow.bridge.contents.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.widget.TabHost
import com.cow.bridge.R
import kotlinx.android.synthetic.main.video_contents.*

class videoContentsMain :FragmentActivity(){
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.video_contents)

        host.setup()

        val tab_comments : TabHost.TabSpec = host.newTabSpec("tabComments")
        val tab_upnext : TabHost.TabSpec = host.newTabSpec("tabUpNext")

        tab_comments.setIndicator("Comments")
        tab_upnext.setIndicator("Up Next")

        host.addTab(tab_comments)
        host.addTab(tab_upnext)

        host.currentTab = 0
    }
}