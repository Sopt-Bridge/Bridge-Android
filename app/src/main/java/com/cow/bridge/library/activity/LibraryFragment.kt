package com.cow.bridge.library.activity


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cow.bridge.R
import com.cow.bridge.library.adapter.LibraryFolderAdapter
import com.cow.bridge.library.adapter.RecentVideoAdapter
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import kotlinx.android.synthetic.main.fragment_library.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LibraryFragment : Fragment() {
    var api : ServerInterface? = ApplicationController.instance?.buildServerInterface()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val convertView = inflater!!.inflate(R.layout.fragment_library, container, false)

        with(convertView){

            val recentVideoAdapter = RecentVideoAdapter(context)
            val libraryFolderAdapter = LibraryFolderAdapter(context)

            val llm : LinearLayoutManager = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.HORIZONTAL
            library_recycler_recent.layoutManager = llm
            library_recycler_recent.adapter = recentVideoAdapter
            val llm3 : RecyclerView.LayoutManager = GridLayoutManager(context, 2)
            library_recycler_folder.layoutManager = llm3
            library_recycler_folder.adapter = libraryFolderAdapter

            var messagesCall = api?.recommendedContentsList()
            messagesCall?.enqueue(object : Callback<Network> {
                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    if(network?.message.equals("ok")){
                        network.data?.get(0)?.contents_list?.let {
                            if(it.size!=0){
                                recentVideoAdapter.addAll(it)
                                recentVideoAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<Network>?, t: Throwable?) {

                }
            })

            messagesCall = api?.recentContentsList(0, 0)
            messagesCall?.enqueue(object : Callback<Network>{
                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    if(network?.message.equals("ok")){
                        network.data?.get(0)?.group_list?.let {
                            if(it.size!=0){
                                libraryFolderAdapter.addAll(it)
                                libraryFolderAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<Network>?, t: Throwable?) {

                }
            })
        }

        return convertView
    }


}
