package com.cow.bridge.request.activity

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.cow.bridge.R
import com.cow.bridge.contents.adapter.ImageContentsCommentAdapter
import com.cow.bridge.model.ContentsComment
import com.cow.bridge.model.Request
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import com.cow.bridge.request.adapter.RequestAdapter
import com.cow.bridge.request.adapter.RequestCommentAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_image_contents.*
import kotlinx.android.synthetic.main.activity_request_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestContentActivity : AppCompatActivity() {
    val api: ServerInterface? = ApplicationController.instance?.buildServerInterface()
    var requestCommentAdapter : RequestCommentAdapter? = null
    var request: Request? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_content)

        var clickId: ArrayList<View> = ArrayList()

        val intent = Intent(this.intent)
        request = intent.getSerializableExtra("request") as Request
        val request_postbtn: Button = findViewById(R.id.request_contents_comment_post_btn)


        request_text_title.text = request?.iboardTitle
        request_text_date.text = request?.iboardDate.toString()
        request_text_name.text = "${request?.userName}"
        request_text_link.text = request?.iboardUrl
        request_text_content.text = request?.iboardContent

        clickId.add(request_postbtn)


        requestCommentAdapter = RequestCommentAdapter(this@RequestContentActivity)

        val llm: LinearLayoutManager = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        request_comments_list.layoutManager = llm
        request_comments_list.adapter = requestCommentAdapter

        getRequestCommentList()


    }

    //request 댓글 불러오기
    fun getRequestCommentList() {
        var messagesCall = api?.getrequestComment(request?.iboardIdx!!, 0)
        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                if (network?.message.equals("ok")) {
                    network.data?.get(0)?.request_list?.let {
                        if (it.size != 0) {
                            requestCommentAdapter?.clear()
                            requestCommentAdapter?.addAll(it)
                            requestCommentAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Network>?, t: Throwable?) {
                Log.v("test fail : ", t.toString())
            }
        })
    }
}




