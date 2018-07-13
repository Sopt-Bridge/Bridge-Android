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
import com.cow.bridge.model.ContentsComment
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.ServerInterface
import com.cow.bridge.util.UtilController
import java.text.SimpleDateFormat

class VideoContentsCommentAdapter(val context : Context ) : RecyclerView.Adapter<VideoContentsCommentAdapter.VideoContentsCommentViewHolder>(){
    var items = ArrayList<ContentsComment>()
    val api : ServerInterface? = ApplicationController.instance?.buildServerInterface()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoContentsCommentViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.row_video_contents_comment,parent,false)
        return VideoContentsCommentViewHolder(mainView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VideoContentsCommentViewHolder, position: Int) {
        with((holder as VideoContentsCommentViewHolder).itemView) {
            video_contents_comment_tv_user_name.text = items[position].userName
            video_contents__comment_tv_user_comment.text = items[position].CcmtContent
            video_contents_comment_tv_comment_date.text = UtilController.timeformat(items[position].CcmtDate)

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
}