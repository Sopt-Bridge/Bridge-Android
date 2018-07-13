package com.cow.bridge.library.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cow.bridge.R
import com.cow.bridge.library.dialog.LibraryAddContentsDialog
import com.cow.bridge.library.dialog.LibraryAddFolderDialog
import com.cow.bridge.model.Group
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import kotlinx.android.synthetic.main.row_library_add_contents_simple.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class LibraryAddContentsAdapter(internal var _context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = ArrayList<Group>()
    var api = ApplicationController.instance?.buildServerInterface()
    var contentsIdx : Int? = null

    internal var onLibraryFolderItemClickListener: OnLibraryFolderItemClickListener? = null

    fun setOnLibraryFolderItemClickListener(onLibraryFolderItemClickListener: OnLibraryFolderItemClickListener) {
        this.onLibraryFolderItemClickListener = onLibraryFolderItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.row_library_add_contents_simple, parent, false)
        return LibraryFolderViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as LibraryFolderViewHolder).itemView){

            library_layout_main.setOnClickListener {
                var messagesCall = api?.addGroupContents(Group(items[position].groupIdx, contentsIdx!!))
                messagesCall?.enqueue(object : Callback<Network> {
                    override fun onFailure(call: Call<Network>?, t: Throwable?) {
                        Log.v("test fail : ", t.toString())
                    }

                    override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                        var network = response!!.body()
                        if(network?.message.equals("ok")) {
                            Toast.makeText(_context, "Add a Contents in ${items[position].groupTitle}", Toast.LENGTH_SHORT).show()
                            if (onLibraryFolderItemClickListener != null) {
                                onLibraryFolderItemClickListener!!.onLibraryFolderItemClickListener(true)
                            }

                        }
                    }
                })
            }
            library_text_name.text = items[position].groupTitle


        }
    }

    override fun getItemCount(): Int {
        //return 5
        return items.size
    }

    fun setContentsIdx(contentsIdx : Int){
        this.contentsIdx = contentsIdx
    }

    fun clear(){
        this.items.clear()
    }

    fun addAll(groups: java.util.ArrayList<Group>) {
        this.items.addAll(groups)
    }


    private inner class LibraryFolderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


    interface OnLibraryFolderItemClickListener {
        fun onLibraryFolderItemClickListener(addContents : Boolean)
    }

}