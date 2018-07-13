package com.cow.bridge.library.activity

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.cow.bridge.R
import com.cow.bridge.library.adapter.GroupContentsAdapter
import com.cow.bridge.library.dialog.LibraryDeleteDialog
import com.cow.bridge.library.dialog.LibraryDialog
import com.cow.bridge.model.Group
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_group_detail.*
import kotlinx.android.synthetic.main.row_libraryfolder_simple.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupDetailActivity : AppCompatActivity() {
    var api = ApplicationController.instance?.buildServerInterface()
    var groupContentsAdapter : GroupContentsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_detail)

        val intent = Intent(getIntent())
        var group = intent.getSerializableExtra("group") as Group


        group_text_name.text = group.groupTitle
        try{
            group_image_bg.setBackgroundColor(Color.parseColor(group.groupColor))
        }catch (e : IllegalArgumentException){
            group_image_bg.setBackgroundColor(Color.parseColor("#F7F7F7"))
        }
        Glide.with(this@GroupDetailActivity).load(group.groupBgimage).into(group_image_thumbnail)

        groupContentsAdapter = GroupContentsAdapter(this@GroupDetailActivity)

        val llm : LinearLayoutManager = LinearLayoutManager(this@GroupDetailActivity)
        llm.orientation = LinearLayoutManager.VERTICAL
        group_recycler.layoutManager = llm
        group_recycler.adapter = groupContentsAdapter

        var sp : SharedPreferences = getSharedPreferences("bridge", AppCompatActivity.MODE_PRIVATE)
        var messagesCall = api?.getGroupContentsList(0, sp.getInt("userIdx", 0), group.groupIdx)
        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                Log.v("getGroupContentsList", Gson().toJson(network))
                if(network?.message.equals("ok")){
                    network.data?.get(0)?.contents_list?.let {
                        if(it.size!=0){
                            groupContentsAdapter?.clear()
                            groupContentsAdapter?.addAll(it)
                            groupContentsAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Network>?, t: Throwable?) {

            }
        })

        group_text_delete.setOnClickListener {
            val libraryDeleteDialog : LibraryDeleteDialog = LibraryDeleteDialog(this@GroupDetailActivity)
            libraryDeleteDialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            libraryDeleteDialog.show()
            libraryDeleteDialog.setOnDismissListener(object : DialogInterface.OnDismissListener{
                override fun onDismiss(dialog: DialogInterface?) {
                    with(dialog as LibraryDeleteDialog){
                        if(confirm){
                            var groupTmp = Group(group.groupIdx)
                            groupTmp.userIdx = sp.getInt("userIdx", 0)
                            Log.v("test", "${groupTmp.groupIdx} ${groupTmp.userIdx}")
                            var messagesCall = api?.deleteGroup(groupTmp)
                            messagesCall?.enqueue(object : Callback<Network> {
                                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                                    var network = response!!.body()
                                    Log.v("test", Gson().toJson(network))
                                    if(network?.message.equals("ok")){
                                        finish()
                                    }
                                }
                                override fun onFailure(call: Call<Network>?, t: Throwable?) {

                                }
                            })
                        }
                    }
                }

            })

        }

        group_text_edit.setOnClickListener {
            val libraryDialog : LibraryDialog = LibraryDialog(this@GroupDetailActivity, "#FFFFFF", group.groupTitle)
            libraryDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            libraryDialog.show()
            libraryDialog.setOnDismissListener {dialog ->
                with(dialog as LibraryDialog){
                    if(confirm){
                        var sp : SharedPreferences = getSharedPreferences("bridge", AppCompatActivity.MODE_PRIVATE)
                        var messagesCall = api?.modifyGroup(Group(sp.getInt("userIdx", 0), group.groupIdx, groupName!!, groupColor!!))
                        messagesCall?.enqueue(object : Callback<Network> {
                            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                                var network = response!!.body()
                                if(network?.message.equals("ok")){
                                    this@GroupDetailActivity.group_text_name.text = groupName
                                    try{
                                        this@GroupDetailActivity.group_image_bg.setBackgroundColor(Color.parseColor(groupColor))
                                    }catch (e : IllegalArgumentException){
                                        //this@GroupDetailActivity.group_image_bg.setBackgroundColor(Color.parseColor("#F7F7F7"))
                                    }
                                }
                            }
                            override fun onFailure(call: Call<Network>?, t: Throwable?) {
                                Log.v("test", t.toString())

                            }
                        })
                    }
                }
            }
        }

        group_image_back.setOnClickListener { finish() }

    }
}
