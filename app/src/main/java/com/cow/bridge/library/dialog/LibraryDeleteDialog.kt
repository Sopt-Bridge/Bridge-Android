package com.cow.bridge.library.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import com.cow.bridge.R
import kotlinx.android.synthetic.main.dialog_delete_library.*

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class LibraryDeleteDialog(context: Context) : Dialog(context), View.OnClickListener {

    var confirm : Boolean = false
    var _context = context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_delete_library)

        library_button_cancel.setOnClickListener(this)
        library_button_delete.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.library_button_cancel -> {
                dismiss()
            }
            R.id.library_button_delete -> {
                confirm = true
                dismiss()
            }

        }
    }

}