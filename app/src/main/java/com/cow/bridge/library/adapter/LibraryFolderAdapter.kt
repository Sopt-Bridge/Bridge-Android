package com.cow.bridge.library.adapter

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Point
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.bumptech.glide.Glide
import com.cow.bridge.R
import com.cow.bridge.library.activity.GroupDetailActivity
import com.cow.bridge.model.Group
import com.cow.bridge.util.UtilController
import com.google.gson.Gson
import kotlinx.android.synthetic.main.row_libraryfolder_simple.view.*

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class LibraryFolderAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = ArrayList<Group>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.row_libraryfolder_simple, parent, false)
        return LibraryFolderViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        with((holder as LibraryFolderViewHolder).itemView){
            folder_layout_main.post(object : Runnable{
                override fun run() {
                    val wm : WindowManager = _context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    var display : Display = wm.defaultDisplay
                    var size : Point = Point()
                    display.getSize(size)
                    var width : Int = size.x
                    var params : ViewGroup.LayoutParams = folder_layout_main.layoutParams
                    params.width = width.toInt()/2 - UtilController.convertDpToPixel(23f, _context).toInt()
                    folder_layout_main.layoutParams = params

                    //glideRequestManager.load(ApplicationController.pictureEndpoint+"/mentors/"+mentoringMentor.getMentorBackground()).into(((MentoringViewHolder) holder).backgroundImage);
                }

            })

            folder_layout_main.setOnClickListener {
                val intent = Intent(_context, GroupDetailActivity::class.java)
                intent.putExtra("group", items[position])
                (_context as Activity).startActivity(intent)
            }

            //Glide.with(_context).load(items[position].groupBgimage).into(folder_image_thumbnail)
            folder_text_groupname.text = items[position].groupTitle
            items[position].groupColor?.let{
                var color = if(items[position].groupColor.startsWith("#")) items[position].groupColor else "#${items[position].groupColor}"
                try{
                    folder_layout_main.setBackgroundColor(Color.parseColor(color))
                }catch (e : IllegalArgumentException){
                    folder_layout_main.setBackgroundColor(Color.parseColor("#F7F7F7"))
                }
            }

        }

    }

    override fun getItemCount(): Int {
        //return 5
        return items.size
    }

    fun clear(){
        this.items.clear()
    }

    fun addAll(groups: java.util.ArrayList<Group>) {
        this.items.addAll(groups)
    }


    private inner class LibraryFolderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}