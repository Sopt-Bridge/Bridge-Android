package com.cow.bridge.request.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.cow.bridge.R
import com.cow.bridge.model.Request
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import com.cow.bridge.request.adapter.RequestCommentAdapter
import com.cow.bridge.util.UtilController
import com.google.gson.Gson
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
        request_text_date.text = UtilController.timeformat(request?.iboardDate)
        request_text_name.text = "${request?.userName}"
        request_text_link.text = request?.iboardUrl
        request_text_content.text = request?.iboardContent

        request_image_back.setOnClickListener { finish() }
        clickId.add(request_postbtn)


        requestCommentAdapter = RequestCommentAdapter(this@RequestContentActivity)

        val llm: LinearLayoutManager = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        request_comments_list.layoutManager = llm
        request_comments_list.adapter = requestCommentAdapter

        getRequestCommentList()

        //request 댓글 post하기
        request_postbtn.setOnClickListener {
            var requestTmp = Request()

            if (request_comment_edit.text.toString().trim().equals("")) {
                Toast.makeText(applicationContext, "enter the comment", Toast.LENGTH_SHORT).show()
            } else {
                requestTmp.icmtContent= (request_comment_edit.text.toString())
                requestTmp.iboardIdx= request?.iboardIdx!!


                var sp : SharedPreferences = getSharedPreferences("bridge", MODE_PRIVATE)
                var myUserIdx = sp.getInt("userIdx", 0)
                requestTmp.userIdx = myUserIdx

                var messagesCall = api?. requestCommentWrite(requestTmp)
                messagesCall?.enqueue(object : Callback<Network> {
                    override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                        var network = response!!.body()
                        Log.v("requestCommentWrite", Gson().toJson(network))
                        if (network?.message.equals("ok")) {
                            getRequestCommentList()
                        } else {
                            Toast.makeText(applicationContext, "error : ${network?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Network>?, t: Throwable?) {
                        Toast.makeText(applicationContext, "error : ${t.toString()}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    //request 댓글 불러오기
    fun getRequestCommentList() {
        var messagesCall = api?.getrequestComment(request?.iboardIdx!!, 0)
        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                Log.v("getrequestComment", Gson().toJson(network))
                if (network?.message.equals("ok")) {
                    network.data?.get(0)?.request_comment_list?.let {
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




