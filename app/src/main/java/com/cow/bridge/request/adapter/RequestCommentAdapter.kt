package com.cow.bridge.request.adapter

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cow.bridge.R
import com.cow.bridge.model.ContentsComment
import com.cow.bridge.model.Request
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.request.activity.RequestContentActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_image_comments.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestCommentAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var items = ArrayList<Request>()
    var api = ApplicationController.instance?.buildServerInterface()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.fragment_image_comments, parent, false)
        return RequestCommentViewHolder(convertView)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as RequestCommentViewHolder).itemView){

            image_comment_name.text = items[position].userName
            image_comment_text_title.text = items[position].icmtContent
            image_contents_tv_comment_date.text = items[position].icmtDate.toString()


            var sp : SharedPreferences = (_context as Activity).getSharedPreferences("bridge", Context.MODE_PRIVATE)
            var myUserIdx = sp.getInt("userIdx", 0)
            if(items[position].userIdx==myUserIdx){
                image_comment_delete.visibility = View.VISIBLE
            }else{
                image_comment_delete.visibility = View.GONE
            }

            image_comment_delete.setOnClickListener {
                var messagesCall = api?.contentsCommentDelete(ContentsComment(myUserIdx, items[position].iboardIdx))
                messagesCall?.enqueue(object : Callback<Network> {
                    override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                        var network = response!!.body()
                        Log.v("requestCommentDelet", Gson().toJson(network))
                        if(network?.message.equals("ok")){
                            Toast.makeText(_context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                            (_context as RequestContentActivity).getRequestCommentList()
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

    fun addAll(requestComment: java.util.ArrayList<Request>) {
        this.items.addAll(requestComment)
    }

    private inner class RequestCommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }



}