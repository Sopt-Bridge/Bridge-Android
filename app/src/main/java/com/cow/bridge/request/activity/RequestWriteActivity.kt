package com.cow.bridge.request.activity

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.cow.bridge.R
import com.cow.bridge.model.Request
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.util.UtilController
import kotlinx.android.synthetic.main.activity_request_write.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RequestWriteActivity : AppCompatActivity() {
    val api = ApplicationController.instance?.buildServerInterface()
    var titleFlag = false
    var linkFlag = false
    var contentFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_write)


        var sp : SharedPreferences = getSharedPreferences("bridge", MODE_PRIVATE)

        request_text_name.text = sp.getString("loginName", "")
        request_text_date.text = UtilController.timeformat(Date())

        request_edit_title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0?.length!! >0){
                    titleFlag = true
                }else{
                    titleFlag = false
                }

                if(titleFlag && linkFlag && contentFlag){
                    request_text_write.setTextColor(Color.parseColor("#E31C9E"))
                }else{
                    request_text_write.setTextColor(Color.parseColor("#333333"))
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        request_edit_url.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0?.length!! >0){
                    linkFlag = true
                }else{
                    linkFlag = false
                }
                if(titleFlag && linkFlag && contentFlag){
                    request_text_write.setTextColor(Color.parseColor("#E31C9E"))
                }else{
                    request_text_write.setTextColor(Color.parseColor("#333333"))
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        request_edit_content.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0?.length!! >0){
                    contentFlag = true
                }else{
                    contentFlag = false
                }
                if(titleFlag && linkFlag && contentFlag){
                    request_text_write.setTextColor(Color.parseColor("#E31C9E"))
                }else{
                    request_text_write.setTextColor(Color.parseColor("#333333"))
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

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
                var sp : SharedPreferences = getSharedPreferences("bridge", MODE_PRIVATE)
                var myUserIdx = sp.getInt("userIdx", 0)

                requestTmp.userIdx = myUserIdx

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
