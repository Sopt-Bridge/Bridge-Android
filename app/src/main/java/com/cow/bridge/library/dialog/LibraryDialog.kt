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
import kotlinx.android.synthetic.main.dialog_library.*

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class LibraryDialog(context: Context, color : String, groupName : String) : Dialog(context), View.OnClickListener {

    var color : String? = null
    var confirm : Boolean = false
    var groupName : String? = null
    var groupColor : String? = null
    var _context = context


    init {
        this.color = color;
        this.groupName = groupName;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_library)

        library_text_done.setOnClickListener(this)

        library_pickerview.setColorListener { pickerColor ->
            library_layout_color.setBackgroundColor(Color.parseColor("#${library_pickerview.colorHtml}"));
            library_edit_color.setText("#${library_pickerview.colorHtml}")
        }

        library_edit_name.setText(groupName)

        library_edit_color.setOnEditorActionListener { textView, i, keyEvent ->
            if(library_edit_color.text.toString().trim().startsWith("#")) {
                try{
                    var colorStr = Color.parseColor(library_edit_color.text.toString().trim())
                    library_layout_color.setBackgroundColor(colorStr);
                }catch (e : IllegalArgumentException){
                    Toast.makeText(_context, "색깔입력형식이 틀렸습니다", Toast.LENGTH_SHORT).show()
                }

            }
            true
        }

    }

    override fun onClick(v: View?) {
        when(v?.id){

            //R.id.orderby_button_cancel -> {
            //    orderby = null
            //    dismiss()
            //}
            R.id.library_text_done -> {
                confirm = true
                groupName = library_edit_name.text.toString().trim()
                Log.v("text", library_edit_color.text.toString().trim())
                groupColor = library_edit_color.text.toString().trim()
                dismiss()
            }

        }
    }

}