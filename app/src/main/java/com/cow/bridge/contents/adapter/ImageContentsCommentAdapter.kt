package com.cow.bridge.contents.adapter

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cow.bridge.R
import com.cow.bridge.contents.activity.ImageContentsActivity
import com.cow.bridge.model.ContentsComment
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_image_comments.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageContentsCommentAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var items = ArrayList<ContentsComment>()
    var api = ApplicationController.instance?.buildServerInterface()

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


            var sp : SharedPreferences = (_context as Activity).getSharedPreferences("bridge", MODE_PRIVATE)
            var myUserIdx = sp.getInt("userIdx", 0)
            if(items[position].userIdx==myUserIdx){
                image_comment_delete.visibility = View.VISIBLE
            }else{
                image_comment_delete.visibility = View.GONE
            }

            image_comment_delete.setOnClickListener {
                var messagesCall = api?.contentsCommentDelete(ContentsComment(myUserIdx, items[position].ccmtIdx))
                messagesCall?.enqueue(object : Callback<Network> {
                    override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                        var network = response!!.body()
                        Log.v("contentsCommentDelet", Gson().toJson(network))
                        if(network?.message.equals("ok")){
                            Toast.makeText(_context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                            (_context as ImageContentsActivity).getContentsCommentList()
                        }
                    }
                    override fun onFailure(call: Call<Network>?, t: Throwable?) {

                    }
                })
            }
        }
    }


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