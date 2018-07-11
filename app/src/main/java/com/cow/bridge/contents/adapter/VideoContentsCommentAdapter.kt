package com.cow.bridge.contents.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.model.VideoContentsCommentData
import kotlinx.android.synthetic.main.row_video_contents_comment.view.*
import com.cow.bridge.R
import com.cow.bridge.model.Content
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.ServerInterface

class VideoContentsCommentAdapter(val context : Context, val commentDataItem : ArrayList<VideoContentsCommentData> ) : RecyclerView.Adapter<VideoContentsCommentAdapter.VideoContentsCommentViewHolder>(){
    var items = ArrayList<Content>()
    val api : ServerInterface? = ApplicationController.instance?.buildServerInterface()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VideoContentsCommentViewHolder {
            val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.row_video_contents_comment,parent,false)
            return VideoContentsCommentViewHolder(mainView)
        }

    override fun getItemCount(): Int = commentDataItem.size

    override fun onBindViewHolder(holder: VideoContentsCommentViewHolder?, position: Int) {
        with((holder as VideoContentsCommentViewHolder).itemView) {

        }

    }

        inner class VideoContentsCommentViewHolder(val view : View):RecyclerView.ViewHolder(view){

        }
}