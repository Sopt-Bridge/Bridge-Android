package com.cow.bridge.contents.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.model.VideoContentsVideoData
import com.cow.bridge.R

class VideoContentsVideoAdapter(val context : Context, val videoDataItem : ArrayList<VideoContentsVideoData> ) : RecyclerView.Adapter<VideoContentsVideoAdapter.VideoContentsVideoViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VideoContentsVideoViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.row_video_contents_video,parent,false)
        return VideoContentsVideoViewHolder(mainView)
    }

    override fun getItemCount(): Int = videoDataItem.size

    override fun onBindViewHolder(holder: VideoContentsVideoViewHolder?, position: Int) {

    }

    inner class VideoContentsVideoViewHolder(val itemView : View?):RecyclerView.ViewHolder(itemView){

    }
}