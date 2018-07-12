package com.cow.bridge.contents.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import com.cow.bridge.R
import kotlinx.android.synthetic.main.dialog_feed_back.*

class FeedBackDialog(context: Context) : Dialog(context), View.OnClickListener {

    var feedback : String? = null
    var send : Boolean = false

    init {
        this.feedback = feedback;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_feed_back)

        when(feedback){
        }

        feedback_X_Btn.setOnClickListener(this)
        feedback_Send_Btn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){

            R.id.feedback -> {

            }
            R.id.feedback_X_Btn -> {
                feedback = null
                dismiss()
            }
            R.id.feedback_Send_Btn -> {
                send = true
                dismiss()
            }

        }
    }

}
