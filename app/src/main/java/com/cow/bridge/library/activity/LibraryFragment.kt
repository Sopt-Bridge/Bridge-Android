package com.cow.bridge.library.activity


import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cow.bridge.MainActivity

import com.cow.bridge.R
import com.cow.bridge.library.adapter.LibraryFolderAdapter
import com.cow.bridge.library.adapter.RecentVideoAdapter
import com.cow.bridge.library.dialog.LibraryDialog
import com.cow.bridge.model.Group
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.fragment_library.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LibraryFragment : Fragment() {
    var api : ServerInterface? = ApplicationController.instance?.buildServerInterface()
    var recentVideoAdapter : RecentVideoAdapter? = null
    var libraryFolderAdapter : LibraryFolderAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val convertView = inflater!!.inflate(R.layout.fragment_library, container, false)

        with(convertView){

            recentVideoAdapter = RecentVideoAdapter(context)
            libraryFolderAdapter = LibraryFolderAdapter(context)

            val llm : LinearLayoutManager = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.HORIZONTAL
            library_recycler_recent.layoutManager = llm
            library_recycler_recent.adapter = recentVideoAdapter
            val llm3 : RecyclerView.LayoutManager = GridLayoutManager(context, 2)
            library_recycler_folder.layoutManager = llm3
            library_recycler_folder.adapter = libraryFolderAdapter

            library_image_add.setOnClickListener {
                val libraryDialog : LibraryDialog = LibraryDialog(activity!!, "#FFFFFF", if(libraryFolderAdapter?.getEmpty()!!) "Group ${libraryFolderAdapter?.itemCount!!}" else "Group ${libraryFolderAdapter?.itemCount!!+1}")
                libraryDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                libraryDialog.show()
                libraryDialog.setOnDismissListener {dialog ->
                    with(dialog as LibraryDialog){
                        if(confirm){
                            var sp : SharedPreferences = (_context as Activity).getSharedPreferences("bridge", AppCompatActivity.MODE_PRIVATE)
                            var messagesCall = api?.addGroup(Group(sp.getInt("userIdx", 0), groupName!!, groupColor!!))
                            messagesCall?.enqueue(object : Callback<Network> {
                                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                                    var network = response!!.body()
                                    if(network?.message.equals("ok")){
                                        getGroupList()
                                    }
                                }
                                override fun onFailure(call: Call<Network>?, t: Throwable?) {

                                }
                            })
                        }
                    }
                }

            }

        }

        return convertView
    }

    override fun onResume() {
        super.onResume()
        var sp : SharedPreferences = (activity as MainActivity).getSharedPreferences("bridge", AppCompatActivity.MODE_PRIVATE)
        var messagesCall = api?.getRecentVideoList(sp.getInt("userIdx", 0))
        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                if(network?.message.equals("ok")){
                    network.data?.get(0)?.contents_list?.let {
                        if(it.size!=0){
                            recentVideoAdapter?.clear()
                            recentVideoAdapter?.addAll(it)
                            recentVideoAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Network>?, t: Throwable?) {

            }
        })

        getGroupList()
    }

    fun getGroupList(){
        var sp : SharedPreferences = (activity as MainActivity).getSharedPreferences("bridge", AppCompatActivity.MODE_PRIVATE)
        var messagesCall = api?.getGroupList(sp.getInt("userIdx", 0))
        messagesCall?.enqueue(object : Callback<Network>{
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                Log.v("getGroupList : ", Gson().toJson(network))
                if(network?.message.equals("ok")){
                    network.data?.get(0)?.group_list?.let {
                        if(it.size!=0){
                            val llm3 : RecyclerView.LayoutManager = GridLayoutManager(context, 2)
                            library_recycler_folder.layoutManager = llm3
                            library_recycler_folder.adapter = libraryFolderAdapter

                            libraryFolderAdapter?.clear()
                            libraryFolderAdapter?.setEmpty(false)
                            libraryFolderAdapter?.addAll(it)
                            libraryFolderAdapter?.notifyDataSetChanged()
                        }else{

                            val llm3 : RecyclerView.LayoutManager = GridLayoutManager(context, 1)
                            library_recycler_folder.layoutManager = llm3
                            library_recycler_folder.adapter = libraryFolderAdapter

                            libraryFolderAdapter?.clear()
                            var noresult = ArrayList<Group>()
                            noresult.add(Group())
                            libraryFolderAdapter?.setEmpty(true)
                            libraryFolderAdapter?.addAll(noresult)
                            libraryFolderAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Network>?, t: Throwable?) {

            }
        })
    }
}
