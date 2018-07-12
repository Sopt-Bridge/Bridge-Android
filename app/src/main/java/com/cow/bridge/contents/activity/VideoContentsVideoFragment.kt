package com.cow.bridge.contents.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.R
import com.cow.bridge.contents.adapter.VideoContentsVideoAdapter
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.ServerInterface
import kotlinx.android.synthetic.main.fragment_other.view.*
import kotlinx.android.synthetic.main.fragment_video_contents_comment.*

class VideoContentsVideoFragment : Fragment() {
    val api : ServerInterface? = ApplicationController.instance?.buildServerInterface()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val convertView = inflater!!.inflate(R.layout.fragment_video_contents_video, container, false)

        //val llm : RecyclerView.LayoutManager = GridLayoutManager(context, 2)
        //video_contents_video_recycler.layoutManager = llm
        //video_contents_video_recycler.adapter = VideoContentsVideoAdapter()

        return convertView
    }
}