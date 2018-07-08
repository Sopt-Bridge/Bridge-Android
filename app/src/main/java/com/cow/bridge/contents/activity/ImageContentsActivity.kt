package com.cow.bridge.contents.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.cow.bridge.R

class ImageContentsActivity : AppCompatActivity() {
    private lateinit var cancelButton : ImageButton

    var clickId : ArrayList<View> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_contents)

        cancelButton = findViewById(R.id.imgContentsBackBtn)
        cancelButton.setOnClickListener{finish()}

        val text1 : TextView = findViewById(R.id.imgCount)
        val text2 : ImageButton = findViewById(R.id.imgLibraryBtn)
        val text3 : ImageButton = findViewById(R.id.imgFeedbackBtn)
        val text4 : TextView = findViewById(R.id.imgDes)
        val text5 : ImageButton = findViewById(R.id.imgLike)
        val text6 : TextView = findViewById(R.id.imgLikeNum)
        val text7 : TextView = findViewById(R.id.imgCredit)

        clickId.add(text1)
        clickId.add(text2)
        clickId.add(text3)
        clickId.add(text4)
        clickId.add(text5)
        clickId.add(text6)
        clickId.add(text7)

        val container : LinearLayout = findViewById(R.id.imgMain)
        container.setOnClickListener {
            for(s in clickId) {
                if(s.visibility == View.VISIBLE) {
                    s.visibility = View.INVISIBLE
                }
                else s.visibility = View.VISIBLE
            }
        }
        
    }

    override fun onStart() {
        super.onStart()

    }

}
