package com.cow.bridge.contents.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.R
import com.cow.bridge.model.ContentsComment
import kotlinx.android.synthetic.main.fragment_image_comments.view.*

class ImageContentsCommentAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var items = ArrayList<ContentsComment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.fragment_image_comments, parent, false)
        return ImageContentsCommentViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as ImageContentsCommentViewHolder).itemView){

            image_comment_name.text = items[position].userName
            image_comment_text_title.text = items[position].ccmtContent
            image_contents_tv_comment_date.text = items[position].ccmtDate.toString()
            //image_contents_btn_recomment.text =  "${items[position].recommentCnt} Replies"

        }
    }
//    var sp : SharedPreferences = (_context as Activity).getSharedPreferences("bridge", MODE_PRIVATE)
//    var myUserIdx = sp.getInt("userIdx", 0)


    override fun getItemCount(): Int {
        return items.size
    }

    fun clear(){
        this.items.clear()
    }

    fun addAll(contentsComment: java.util.ArrayList<ContentsComment>) {
        this.items.addAll(contentsComment)
    }

    private inner class ImageContentsCommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}