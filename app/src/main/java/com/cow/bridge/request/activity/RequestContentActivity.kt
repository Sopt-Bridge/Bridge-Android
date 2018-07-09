package com.cow.bridge.request.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.cow.bridge.R
import com.cow.bridge.model.Request
import kotlinx.android.synthetic.main.activity_request_content.*

class RequestContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_content)

        val intent = Intent(this.intent)
        var request : Request? = intent.getSerializableExtra("request") as Request

        request_text_title.text = request?.iboardTitle
        request_text_date.text = request?.iboardDate.toString()
        request_text_name.text = "${request?.userIdx}"

    }
}
