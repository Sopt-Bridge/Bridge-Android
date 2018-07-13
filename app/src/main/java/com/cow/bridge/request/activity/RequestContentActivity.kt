package com.cow.bridge.request.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.cow.bridge.R
import com.cow.bridge.model.Request
import com.cow.bridge.util.UtilController
import kotlinx.android.synthetic.main.activity_request_content.*

class RequestContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_content)

        val intent = Intent(this.intent)
        var request : Request? = intent.getSerializableExtra("request") as Request

        request_text_title.text = request?.iboardTitle
        request_text_date.text = UtilController.timeformat(request?.iboardDate)
        request_text_name.text = "${request?.userName}"
        request_text_link.text = request?.iboardUrl
        request_text_content.text = request?.iboardContent

        request_image_back.setOnClickListener { finish() }
    }
}
