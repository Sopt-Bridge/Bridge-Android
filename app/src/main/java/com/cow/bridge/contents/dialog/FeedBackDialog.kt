package com.cow.bridge.contents.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cow.bridge.R
import com.cow.bridge.model.Feedback
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_feed_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedBackDialog(context: Context, contentsIdx: Int) : Dialog(context), View.OnClickListener {
    val api : ServerInterface? = ApplicationController.instance?.buildServerInterface()

    var contentsIdx : Int? = null
    var send : Boolean = false
    var _context = context


    init {
        this.contentsIdx = contentsIdx
        this._context = context;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_feed_back)

        val edt : EditText = findViewById(R.id.feedback)
        var btn : Button = findViewById(R.id.feedback_Send_Btn)

        feedback_X_Btn.setOnClickListener(this)
        feedback_Send_Btn.setOnClickListener(this)

        // 버튼 누를 시 send
        btn.setOnClickListener {
            var messagesCall = api?.writeFeedback(Feedback(1, contentsIdx!!, edt.text.toString().trim()))
            messagesCall?.enqueue(object : Callback<Network> {
                override fun onFailure(call: Call<Network>?, t: Throwable?) {
                    Log.v("test fail : ", "nn")

                }
                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    Log.v("feedback test", Gson().toJson(network))
                }
            })
        }


        // Edit Text 10글자 이상 Send Btn 색 변환 & 10글자 전에는 버튼 막음
        edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var text  = s.toString().trim()
                if(text.length >= 10) {
                    btn.setBackgroundResource(R.drawable.round_btn_send_active)
                    btn.setEnabled(true)
                    btn.setOnClickListener {
                        Toast.makeText(context,"피드백을 보냈습니다!",Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                } else if (text.length < 10){
                    btn.setBackgroundResource(R.drawable.round_btn_send)


                }

            }
        })


    }

    override fun onClick(view: View?) {
        when(view?.id){

            R.id.feedback -> {

            }
            R.id.feedback_X_Btn -> {
                dismiss()
            }
            R.id.feedback_Send_Btn -> {
                send = true
                dismiss()
            }

        }
    }

}
