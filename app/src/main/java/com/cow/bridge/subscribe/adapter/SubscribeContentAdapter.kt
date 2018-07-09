package com.cow.bridge.subscribe.adapter

import android.content.Context
import android.graphics.Point
import android.support.v7.widget.RecyclerView
import android.view.*
import com.bumptech.glide.Glide
import com.cow.bridge.R
import kotlinx.android.synthetic.main.row_contents_vertical_simple.view.*

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class SubscribeContentAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.row_contents_vertical_simple, parent, false)
        return SubscribeContentViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        with((holder as SubscribeContentViewHolder).itemView){
            contents_layout_main.post(object : Runnable{
                override fun run() {
                    val wm : WindowManager = _context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    var display : Display = wm.defaultDisplay
                    var size : Point = Point()
                    display.getSize(size)
                    var width : Int = size.x
                    var params : ViewGroup.LayoutParams = contents_layout_main.layoutParams
                    params.width = width.toInt()/2
                    contents_layout_main.layoutParams = params

                    //glideRequestManager.load(ApplicationController.pictureEndpoint+"/mentors/"+mentoringMentor.getMentorBackground()).into(((MentoringViewHolder) holder).backgroundImage);
                }

            })

            val nuestList : Array<Int> = arrayOf(R.drawable.nuest0, R.drawable.nuest1, R.drawable.nuest2, R.drawable.nuest3, R.drawable.nuest4, R.drawable.nuest5, R.drawable.nuest6, R.drawable.nuest7, R.drawable.nuest8, R.drawable.nuest9, R.drawable.nuest10)
            Glide.with(_context).load(nuestList.get(position%11)).fitCenter().into(contents_image_thumbnail)
        }
    }

    override fun getItemCount(): Int {
        return 57
    }


    private inner class SubscribeContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}