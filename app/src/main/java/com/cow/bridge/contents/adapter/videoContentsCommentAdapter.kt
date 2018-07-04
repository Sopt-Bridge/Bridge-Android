package com.cow.bridge.contents.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.R
import com.cow.bridge.model.videoContentsCommentData
import kotlinx.android.synthetic.main.video_contents_comment_layout_manager.view.*

class videoContentsCommentAdapter(val context : Context, val commentDataItem : ArrayList<videoContentsCommentData> ) : RecyclerView.Adapter<videoContentsCommentAdapter.videoContentsCommentViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): videoContentsCommentViewHolder {
            val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.video_contents_comment_layout_manager,parent,false)
            return videoContentsCommentViewHolder(mainView)
        }

        override fun getItemCount(): Int = commentDataItem.size

        override fun onBindViewHolder(holder: videoContentsCommentViewHolder, position: Int) {
            with((holder as videoContentsCommentViewHolder).itemView) {
                video_comment_text_title.text
            }

        }

        inner class videoContentsCommentViewHolder(val view : View):RecyclerView.ViewHolder(view){

        }
}