package com.cow.bridge.contents.activity

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cow.bridge.R
import com.cow.bridge.contents.adapter.VideoContentsCommentAdapter
import com.cow.bridge.model.ContentsComment
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_video_contents_comment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoContentsCommentFragment() :Fragment() {
    private var contentsIdx: Int? = null
    var api : ServerInterface? = null
    var videoContentsCommentAdapter : VideoContentsCommentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            contentsIdx = arguments!!.getInt("contentsIdx")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val convertView = inflater!!.inflate(R.layout.fragment_video_contents_comment,container,false)
        api = ApplicationController.instance?.buildServerInterface()

        with(convertView){
            videoContentsCommentAdapter = VideoContentsCommentAdapter(context)
            val llm : LinearLayoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            video_contents_comment_recycler.layoutManager = llm
            video_contents_comment_recycler.adapter = videoContentsCommentAdapter
            video_contents_comment_recycler.setHasFixedSize(true)
            getCommentList()

            videoContentsCommentAdapter?.setOnCommentDeleteItemClickListener(object : VideoContentsCommentAdapter.OnCommentDeleteItemClickListener{
                override fun onCommentDeleteItemClickListener() {
                    getCommentList()
                }

            })

            video_contents_comment_post_btn.setOnClickListener {
                var commentTmp = ContentsComment()

                if (contents_comment_edit.text.toString().trim().equals("")) {
                    Toast.makeText(activity, "enter the comment", Toast.LENGTH_SHORT).show()
                } else {
                    commentTmp.ccmtContent = (contents_comment_edit.text.toString())
                    commentTmp.contentsIdx = contentsIdx!!
                    var sp : SharedPreferences = activity!!.getSharedPreferences("bridge", MODE_PRIVATE)
                    var myUserIdx = sp.getInt("userIdx", 0)
                    commentTmp.userIdx = myUserIdx

                    var messagesCall = api?.contentsCommentWrite(commentTmp)
                    messagesCall?.enqueue(object : Callback<Network> {
                        override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                            var network = response!!.body()
                            Log.v("contentsCommentWrite", Gson().toJson(network))
                            if (network?.message.equals("ok")) {
                                contents_comment_edit.setText("")
                                getCommentList()
                            } else {
                                Toast.makeText(activity, "error : ${network?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Network>?, t: Throwable?) {
                            Toast.makeText(activity, "error : ${t.toString()}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
        return convertView
    }

    public fun getCommentList(){

        var messagesCall = api?.getContentCommentList(contentsIdx!!,0)
        messagesCall?.enqueue(object : Callback<Network>{
            override fun onFailure(call: Call<Network>?, t: Throwable?) {

            }


            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                if (network?.message.equals("ok")) {
                    network.data?.get(0)?.comments_list?.let {
                        if (it.size != 0) {
                            videoContentsCommentAdapter?.clear()
                            videoContentsCommentAdapter?.addAll(it)
                            videoContentsCommentAdapter?.notifyDataSetChanged()

                        }
                    }
                }
            }
        })
    }

    companion object {

        fun newInstance(param1: Int): VideoContentsCommentFragment {
            val fragment = VideoContentsCommentFragment()
            val args = Bundle()
            args.putSerializable("contentsIdx", param1)
            fragment.arguments = args
            return fragment
        }
    }
}
