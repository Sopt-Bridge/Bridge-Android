package com.cow.bridge.contents.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.model.VideoContentsVideoData
import com.cow.bridge.R
import com.cow.bridge.model.Content
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.ServerInterface
import kotlinx.android.synthetic.main.row_video_contents_video.*

class VideoContentsVideoAdapter(val context : Context, val videoDataItem : ArrayList<VideoContentsVideoData> ) : RecyclerView.Adapter<VideoContentsVideoAdapter.VideoContentsVideoViewHolder>(){
    var items = ArrayList<Content>()
    val api : ServerInterface? = ApplicationController.instance?.buildServerInterface()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VideoContentsVideoViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.row_video_contents_video,parent,false)
        return VideoContentsVideoViewHolder(mainView)
    }

    override fun getItemCount(): Int = videoDataItem.size

    override fun onBindViewHolder(holder: VideoContentsVideoViewHolder?, position: Int) {
        with((holder as VideoContentsVideoViewHolder).itemView) {
            video_contents_video_tv_video_titls.text = items[position].contents_title
            var hashList :String = items.[position].hashName1 + items[position].hashName2 + items[position].hashName3
            video_contents_video_tv_hash.text = hashList
            video_contents_video_tv_contents_time.text = itemts[position].contentsRuntime
            video_contents_video_

        }
    }

    inner class VideoContentsVideoViewHolder(val itemView : View?):RecyclerView.ViewHolder(itemView){

    }
}