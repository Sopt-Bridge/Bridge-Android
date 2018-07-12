package com.cow.bridge.contents.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.R
import com.cow.bridge.model.Content
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import kotlinx.android.synthetic.main.fragment_video_contents_video.*
import kotlinx.android.synthetic.main.fragment_video_contents_video.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoContentsVideoFragment : Fragment() {
    var api : ServerInterface? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View? {
        val convertView : View = inflater!!.inflate(R.layout.fragment_video_contents_video,container,false)
        api = ApplicationController.instance?.buildServerInterface()

        val videoContentsVideoRecycler : RecyclerView = video_contents_video_recycler
        val llm : LinearLayoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        videoContentsVideoRecycler.layoutManager = llm

        var videoList : List<Content> = List<Content>()
        var messagesCall = api?.recommandVideoContentsList(0,2)
        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                if (network?.message.equals("ok")) {
                    network.data?.get(0)?.contents_list?.let {
                        if (it.size != 0) {
                            for(i in 0 .. network.data)
                        }
                    }
                }
            }

        }
        return convertView
    }

}