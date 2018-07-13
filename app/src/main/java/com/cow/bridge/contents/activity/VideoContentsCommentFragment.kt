package com.cow.bridge.contents.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.R
import com.cow.bridge.contents.adapter.VideoContentsCommentAdapter
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import kotlinx.android.synthetic.main.fragment_video_contents_comment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoContentsCommentFragment() :Fragment() {
    private var pageName: String? = null
    var api : ServerInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            pageName = arguments!!.getString(pageName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val convertView = inflater!!.inflate(R.layout.fragment_video_contents_comment,container,false)
        api = ApplicationController.instance?.buildServerInterface()

        with(convertView){
            val videoContentsCommentAdapter : VideoContentsCommentAdapter = VideoContentsCommentAdapter(context)
            val llm : LinearLayoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            video_contents_comment_recycler.layoutManager = llm
            video_contents_comment_recycler.adapter = videoContentsCommentAdapter

            var messagesCall = api?.getContentCommentList(13,0)
            messagesCall?.enqueue(object : Callback<Network>{
                override fun onFailure(call: Call<Network>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    if (network?.message.equals("ok")) {
                        network.data?.get(0)?.comments_list?.let {
                            if (it.size != 0) {
                                videoContentsCommentAdapter.clear()
                                videoContentsCommentAdapter.addAll(it)
                                videoContentsCommentAdapter.notifyDataSetChanged()

                            }
                        }
                    }
                }

        })

    }
        return convertView
    }
}
