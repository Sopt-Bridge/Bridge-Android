package com.cow.bridge.contents.adapter

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cow.bridge.R
import com.cow.bridge.contents.activity.VideoContentsCommentFragment
import com.cow.bridge.contents.activity.VideoContentsMainActivity
import com.cow.bridge.contents.activity.VideoContentsVideoFragment
import com.cow.bridge.model.Content
import com.cow.bridge.model.ContentsComment
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import com.cow.bridge.util.UtilController
import com.google.gson.Gson
import kotlinx.android.synthetic.main.row_video_contents_comment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class VideoContentsCommentAdapter(val _context : Context ) : RecyclerView.Adapter<VideoContentsCommentAdapter.VideoContentsCommentViewHolder>(){
    var items = ArrayList<ContentsComment>()
    var api = ApplicationController.instance?.buildServerInterface()


    internal var onCommentDeleteItemClickListener: OnCommentDeleteItemClickListener? = null

    fun setOnCommentDeleteItemClickListener(onCommentDeleteItemClickListener: OnCommentDeleteItemClickListener) {
        this.onCommentDeleteItemClickListener = onCommentDeleteItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoContentsCommentViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.row_video_contents_comment,parent,false)
        return VideoContentsCommentViewHolder(mainView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VideoContentsCommentViewHolder, position: Int) {
        with((holder as VideoContentsCommentViewHolder).itemView) {
            video_contents_comment_tv_user_name.text = items[position].userName
            video_contents__comment_tv_user_comment.text = items[position].ccmtContent
            video_contents_comment_tv_comment_date.text = UtilController.timeformat(items[position].ccmtDate)


            var sp : SharedPreferences = (_context as Activity).getSharedPreferences("bridge", Context.MODE_PRIVATE)
            var myUserIdx = sp.getInt("userIdx", 0)
            if(items[position].userIdx==myUserIdx){
                video_contents_tv_delete.visibility = View.VISIBLE
            }else{
                video_contents_tv_delete.visibility = View.GONE
            }

            video_contents_tv_delete.setOnClickListener {
                var messagesCall = api?.contentsCommentDelete(ContentsComment(myUserIdx, items[position].ccmtIdx))
                messagesCall?.enqueue(object : Callback<Network> {
                    override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                        var network = response!!.body()
                        Log.v("contentsCommentDelet", Gson().toJson(network))
                        if(network?.message.equals("ok")){
                            Toast.makeText(_context, "Remove a comment", Toast.LENGTH_SHORT).show()
                            if (onCommentDeleteItemClickListener != null) {
                                onCommentDeleteItemClickListener!!.onCommentDeleteItemClickListener()
                            }
                        }
                    }
                    override fun onFailure(call: Call<Network>?, t: Throwable?) {

                    }
                })
            }
        }

    }


    inner class VideoContentsCommentViewHolder(val view : View):RecyclerView.ViewHolder(view){

    }
    fun clear(){
        this.items.clear()
    }

    fun addAll(contentsComment: java.util.ArrayList<ContentsComment>) {
        this.items.addAll(contentsComment)
    }

    interface OnCommentDeleteItemClickListener {
        fun onCommentDeleteItemClickListener()
    }
}