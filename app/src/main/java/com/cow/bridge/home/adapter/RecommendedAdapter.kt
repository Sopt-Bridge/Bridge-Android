package com.cow.bridge.home.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.cow.bridge.R
import com.cow.bridge.contents.activity.ImageContentsActivity
import com.cow.bridge.contents.activity.VideoContentsMainActivity
import com.cow.bridge.model.Content
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.util.UtilController
import kotlinx.android.synthetic.main.row_contents_simple.view.*

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class RecommendedAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = ArrayList<Content>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.row_contents_simple, parent, false)
        return RecommendedViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as RecommendedViewHolder).itemView){

            contents_layout_main.setOnClickListener {

                if(items[position].contentsType==0){
                    val intent = Intent(_context, ImageContentsActivity::class.java)
                    intent.putExtra("imageContents", items[position])
                    (_context as Activity).startActivity(intent)
                }else{
                    val intent = Intent(_context, VideoContentsMainActivity::class.java)
                    intent.putExtra("videoContents", items[position])
                    (_context as Activity).startActivity(intent)
                }
            }

            contents_text_title.text = items[position].contentsTitle
            if(items[position].contentsType==0){
                contents_text_count.text = "+ ${items[position].imgCnt}"
                Glide.with(_context).load(R.drawable.home_image_thumnail_icon).into(contents_image_type)
                Glide.with(_context).load(ApplicationController.imageUrl(items[position].contentsIdx, 1)).override(UtilController.convertDpToPixel(153f, context).toInt(), UtilController.convertDpToPixel(100f, context).toInt()).into(contents_image_thumbnail)
            }else{
                if(items[position].contentsRuntime==null){
                    contents_text_count.text = "00:00"
                }else{
                    contents_text_count.text = items[position].contentsRuntime
                }
                Glide.with(_context).load(R.drawable.home_video_thumnail_icon).into(contents_image_type)
                Glide.with(_context).load(items[position].thumbnailUrl).override(UtilController.convertDpToPixel(153f, context).toInt(), UtilController.convertDpToPixel(100f, context).toInt()).into(contents_image_thumbnail)
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun clear(){
        this.items.clear()
    }

    fun addAll(contents: java.util.ArrayList<Content>) {
        this.items.addAll(contents)
    }

    private inner class RecommendedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}