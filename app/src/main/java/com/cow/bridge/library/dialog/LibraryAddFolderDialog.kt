package com.cow.bridge.library.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import com.cow.bridge.R
import kotlinx.android.synthetic.main.dialog_add_folder_library_contents.*

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class LibraryAddFolderDialog(context: Context) : Dialog(context), View.OnClickListener {

    var confirm : Boolean = false
    var _context = context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_add_folder_library_contents)

        library_button_cancel.setOnClickListener(this)
        library_button_delete.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.library_button_cancel -> {
                dismiss()
            }
            R.id.library_button_delete -> {
                if(library_edit_name.text.toString().trim().length==0){
                    Toast.makeText(_context, "Enter a folder", Toast.LENGTH_SHORT).show()
                }else{
                    //TODO : 폴더 만들고 라이브러리 추가
                    confirm = true
                    dismiss()
                }
            }

        }
    }

}