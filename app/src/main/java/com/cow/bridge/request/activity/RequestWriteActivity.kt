package com.cow.bridge.request.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cow.bridge.R
import com.cow.bridge.model.Request
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import kotlinx.android.synthetic.main.activity_request_write.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestWriteActivity : AppCompatActivity() {
    val api = ApplicationController.instance?.buildServerInterface()
    var request = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_write)

        val intent = Intent(this.intent)
        //val request = intent.getSerializableExtra("request") as Request
        //TODO 세팅
        //request_text_name.text = request.userName
        //request_text_date.text = request.iboardDate.toString()

        request_image_back.setOnClickListener {
            finish()
        }

        request_text_write.setOnClickListener {
            var requestTmp = Request()

            if(request_edit_title.text.toString().trim().equals("")){
                Toast.makeText(applicationContext, "enter the Title", Toast.LENGTH_SHORT).show()
            }else if(request_edit_url.text.toString().trim().equals("")){
                Toast.makeText(applicationContext, "enter the Url", Toast.LENGTH_SHORT).show()
            }else if(request_edit_content.text.toString().trim().equals("")){
                Toast.makeText(applicationContext, "enter the Content", Toast.LENGTH_SHORT).show()
            }else{
                requestTmp.iboardTitle = request_edit_title.text.toString()
                requestTmp.iboardUrl = request_edit_url.text.toString()
                requestTmp.iboardContent = request_edit_content.text.toString()
                requestTmp.userIdx = 1
                //TODO 유저인덱스

                var messagesCall = api?.requestWriteContents(requestTmp)
                messagesCall?.enqueue(object : Callback<Network> {
                    override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                        var network = response!!.body()
                        if(network?.message.equals("ok")){
                            finish()
                        }else{
                            Toast.makeText(applicationContext, "error : ${network.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<Network>?, t: Throwable?) {
                        Toast.makeText(applicationContext, "error : ${t.toString()}", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }


    }
}
