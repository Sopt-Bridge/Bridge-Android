package com.cow.bridge.home.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import com.cow.bridge.R
import kotlinx.android.synthetic.main.dialog_orderby.*

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class OrderbyDialog(context: Context, orderby : String) : Dialog(context), View.OnClickListener {

    var orderby : String? = null
    var confirm : Boolean = false

    init {
        this.orderby = orderby;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_orderby)

        when(orderby){
            "Upload date" -> orderby_button_uploaddate.setBackgroundColor(Color.parseColor("#DDDDDD"))
            "View count" -> orderby_button_viewcount.setBackgroundColor(Color.parseColor("#DDDDDD"))
            "Rating" -> orderby_button_rating.setBackgroundColor(Color.parseColor("#DDDDDD"))
        }

        orderby_button_uploaddate.setOnClickListener(this)
        orderby_button_viewcount.setOnClickListener(this)
        orderby_button_rating.setOnClickListener(this)
        orderby_button_cancel.setOnClickListener(this)
        orderby_button_confirm.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.orderby_button_uploaddate -> {
                orderby = "Upload date"
                orderby_button_uploaddate.setBackgroundColor(Color.parseColor("#DDDDDD"))
                orderby_button_viewcount.setBackgroundColor(Color.parseColor("#FFFFFF"))
                orderby_button_rating.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            R.id.orderby_button_viewcount -> {
                orderby = "View count"
                orderby_button_uploaddate.setBackgroundColor(Color.parseColor("#FFFFFF"))
                orderby_button_viewcount.setBackgroundColor(Color.parseColor("#DDDDDD"))
                orderby_button_rating.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            R.id.orderby_button_rating -> {
                orderby = "Rating"
                orderby_button_uploaddate.setBackgroundColor(Color.parseColor("#FFFFFF"))
                orderby_button_viewcount.setBackgroundColor(Color.parseColor("#FFFFFF"))
                orderby_button_rating.setBackgroundColor(Color.parseColor("#DDDDDD"))
            }
            R.id.orderby_button_cancel -> {
                orderby = null
                dismiss()
            }
            R.id.orderby_button_confirm -> {
                confirm = true
                dismiss()
            }

        }
    }

}
