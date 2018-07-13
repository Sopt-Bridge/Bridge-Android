package com.cow.bridge.contents.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.cow.bridge.R
import com.cow.bridge.contents.activity.VideoContentsMainActivity
import com.cow.bridge.model.Content
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.ServerInterface
import com.cow.bridge.util.UtilController
import kotlinx.android.synthetic.main.row_video_contents_video.view.*

class VideoContentsVideoAdapter(val context : Context) : RecyclerView.Adapter<VideoContentsVideoAdapter.VideoContentsVideoViewHolder>(){
    var items = ArrayList<Content>()
    val api : ServerInterface? = ApplicationController.instance?.buildServerInterface()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoContentsVideoViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.row_video_contents_video,parent,false)
        return VideoContentsVideoViewHolder(mainView)
    }

    override fun onBindViewHolder(holder: VideoContentsVideoViewHolder, position: Int) {
        with((holder as VideoContentsVideoViewHolder).itemView) {
            video_contents_video_tv_video_title.text = items[position].contentsTitle
            var temp_hash = " "
            if(items[position].hashName1 != null) {
                temp_hash = items[position].hashName1
                if(items[position].hashName2 != null) {
                    temp_hash = temp_hash + items[position].hashName2
                    if(items[position].hashName3 != null) {
                        temp_hash = temp_hash + items[position].hashName3
                    }
                }
            }
            video_contents_video_tv_hash.text = temp_hash
            Log.v("test", ApplicationController.videoThumbnailUrl(items[position].contentsIdx))
            Glide.with(context).load(ApplicationController.videoThumbnailUrl(items[position].contentsIdx)).override(UtilController.convertDpToPixel(143f, context).toInt(), UtilController.convertDpToPixel(92f, context).toInt()).into(video_contents_video_iv_video_image)

            if(items[position].contentsRuntime==null){
                video_contents_video_tv_contents_time.text = "00:00"
            }else{
                video_contents_video_tv_contents_time.text = items[position].contentsRuntime
            }

            video_contents_video_layout_main.setOnClickListener {
                val intent = Intent(context, VideoContentsMainActivity::class.java)
                intent.putExtra("videoContents", items[position])
                (context as Activity).startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VideoContentsVideoViewHolder(val view : View):RecyclerView.ViewHolder(view){

    }
    fun clear(){
        this.items.clear()
    }

    fun addAll(content: java.util.ArrayList<Content>) {
        this.items.addAll(content)
    }
}