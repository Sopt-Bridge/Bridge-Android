package com.cow.bridge.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.cow.bridge.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_image_back.setOnClickListener {
            finish()
        }

        login_button_facebook.setOnClickListener {

        }

        login_button_google.setOnClickListener {

        }

    }
}
