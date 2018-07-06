package com.cow.bridge.contents.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.model.videoContentsVideoData
import com.cow.bridge.R

class videoContentsVideoAdapter(val context : Context, val videoDataItem : ArrayList<videoContentsVideoData> ) : RecyclerView.Adapter<videoContentsVideoAdapter.videoContentsVideoViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): videoContentsVideoViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.video_contents_video_layout_manager,parent,false)
        return videoContentsVideoViewHolder(mainView)
    }

    override fun getItemCount(): Int = videoDataItem.size

    override fun onBindViewHolder(holder: videoContentsVideoViewHolder?, position: Int) {

    }

inner class videoContentsVideoViewHolder(val itemView : View?):RecyclerView.ViewHolder(itemView){

}
}