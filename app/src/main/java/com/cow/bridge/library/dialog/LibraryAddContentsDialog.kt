package com.cow.bridge.library.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.Window
import com.cow.bridge.R
import com.cow.bridge.library.adapter.LibraryAddContentsAdapter
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_add_library_contents.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by jihaeseong on 2018. 7. 3..
 */

class LibraryAddContentsDialog(context: Context, contentsIdx : Int) : Dialog(context) {

    var _context = context
    var contentsIdx = contentsIdx
    var api = ApplicationController.instance?.buildServerInterface()
    var addContents : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_add_library_contents)

        val libraryAddContentsAdapter : LibraryAddContentsAdapter = LibraryAddContentsAdapter(context)
        libraryAddContentsAdapter.setOnLibraryFolderItemClickListener(object : LibraryAddContentsAdapter.OnLibraryFolderItemClickListener{
            override fun onLibraryFolderItemClickListener(addContentsTmp: Boolean) {
                addContents = addContentsTmp
                dismiss()
            }

        })
        val llm : LinearLayoutManager = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        library_recycler.layoutManager = llm
        library_recycler.adapter = libraryAddContentsAdapter

        var sp : SharedPreferences = (_context as Activity).getSharedPreferences("bridge", MODE_PRIVATE)
        var myUserIdx = sp.getInt("userIdx", 0)

        var messagesCall = api?.getGroupList(myUserIdx)
        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                Log.v("test", Gson().toJson(network))
                if(network?.message.equals("ok")){
                    network.data?.get(0)?.group_list?.let {
                        if(it.size!=0){
                            libraryAddContentsAdapter.clear()
                            libraryAddContentsAdapter.addAll(it)
                            libraryAddContentsAdapter.setContentsIdx(contentsIdx)
                            libraryAddContentsAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Network>?, t: Throwable?) {

            }
        })

    }

}