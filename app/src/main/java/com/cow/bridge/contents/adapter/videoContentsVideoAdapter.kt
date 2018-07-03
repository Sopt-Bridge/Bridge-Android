package com.cow.bridge.contents.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.model.videoContentsData

class videoContentsVideoAdapter(val context : Context, val dataItem : ArrayList<videoContentsData> ) : RecyclerView.Adapter<videoContentsVideoAdapter.videoContentsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): videoContentsViewHolder {
        val mainView : View = LayoutInflater.from(parent.context).inflate(R.layout.video_contents_video_layout_manager,parent,false)
        return videoContentsViewHolder(mainView)
    }

    override fun getItemCount(): Int = R.model.videoContentsData.size

    override fun onBindViewHolder(holder: videoContentsViewHolder?, position: Int) {

    }

inner class videoContentsViewHolder(val itemView : View?):RecyclerView.ViewHolder(itemView){

}
}