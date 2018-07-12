package com.cow.bridge.library.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.support.v7.widget.RecyclerView
import android.view.*
import com.bumptech.glide.Glide
import com.cow.bridge.R
import com.cow.bridge.contents.activity.ImageContentsActivity
import com.cow.bridge.contents.activity.VideoContentsMainActivity
import com.cow.bridge.model.Content
import com.cow.bridge.model.Group
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.util.UtilController
import kotlinx.android.synthetic.main.row_libraryfolder_simple.view.*

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class LibraryFolderAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = ArrayList<Group>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.row_libraryfolder_simple, parent, false)
        return RecentViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        with((holder as RecentViewHolder).itemView){
            folder_layout_main.post(object : Runnable{
                override fun run() {
                    val wm : WindowManager = _context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    var display : Display = wm.defaultDisplay
                    var size : Point = Point()
                    display.getSize(size)
                    var width : Int = size.x
                    var params : ViewGroup.LayoutParams = folder_layout_main.layoutParams
                    params.width = width.toInt()/2
                    folder_layout_main.layoutParams = params

                    //glideRequestManager.load(ApplicationController.pictureEndpoint+"/mentors/"+mentoringMentor.getMentorBackground()).into(((MentoringViewHolder) holder).backgroundImage);
                }

            })

            folder_layout_main.setOnClickListener {



            }

            Glide.with(_context).load(items[position].groupBgimage).into(folder_image_thumbnail)
            folder_text_groupname.text = items[position].groupTitle
            folder_layout_main.setBackgroundColor(Color.parseColor(items[position].groupColor))
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun clear(){
        this.items.clear()
    }

    fun addAll(groups: java.util.ArrayList<Group>) {
        this.items.addAll(groups)
    }


    private inner class RecentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}