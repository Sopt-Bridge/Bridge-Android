package com.cow.bridge.request.activity


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.SearchView
import android.widget.Toast

import com.cow.bridge.R
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.request.adapter.RequestAdapter
import com.cow.bridge.util.RequestDividerItemDecoration
import com.cow.bridge.util.UtilController
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_request.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [RequestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RequestFragment : Fragment() {
    val api = ApplicationController.instance?.buildServerInterface()
    var requestAdapter : RequestAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val convertView : View = inflater!!.inflate(R.layout.fragment_request, container, false)

        with(convertView){
            requestAdapter = RequestAdapter(context)

            val llm : LinearLayoutManager = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.VERTICAL
            request_recycler.layoutManager = llm
            request_recycler.addItemDecoration(RequestDividerItemDecoration(context))
            request_recycler.adapter = requestAdapter

            //request_search.setIconifiedByDefault(false)
            val searchTextView = request_search.findViewById<AutoCompleteTextView>(request_search.context.resources.getIdentifier("android:id/search_src_text", null, null))
            searchTextView.setTextColor(Color.parseColor("#333333"))
            searchTextView.setTextSize(12f)

            request_search.setOnClickListener {
                request_search.onActionViewExpanded()
            }

            request_search.setOnQueryTextFocusChangeListener() { view, hasFocus ->
                if(hasFocus){
                    request_button_write.setText("search")
                }else{
                    request_button_write.setText("write")
                    request_search.onActionViewCollapsed()
                }
            }

            request_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    request_button_write.callOnClick()
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    if(p0.equals("")){
                        request_button_write.callOnClick()
                    }
                    return false
                }

            })

            request_button_write.setOnClickListener{
                if(request_button_write.text.equals("write")){
                    var intent = Intent(context, RequestWriteActivity::class.java)
                    //intent.putExtra("request", request)
                    startActivityForResult(intent, 0)
                }else if(request_button_write.text.equals("search")){
                    if(request_search.query.toString().equals("")){
                        var messagesCall = api?.requestContentsList(0)
                        messagesCall?.enqueue(object : Callback<Network> {
                            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                                var network = response!!.body()
                                Log.v("requestContentsList : ", Gson().toJson(network))
                                if(network?.message.equals("ok")){
                                    network.data?.get(0)?.request_list?.let {
                                        if(it.size!=0){
                                            requestAdapter?.clear()
                                            requestAdapter?.addAll(it)
                                            requestAdapter?.notifyDataSetChanged()
                                        }
                                    }
                                }
                            }
                            override fun onFailure(call: Call<Network>?, t: Throwable?) {

                            }
                        })
                    }else{
                        var messagesCall = api?.requestSearchContentsList(request_search.query.toString())
                        messagesCall?.enqueue(object : Callback<Network> {
                            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                                var network = response!!.body()
                                Log.v("requestSearchContent : ", Gson().toJson(network))
                                if(network?.message.equals("ok")){
                                    network.data?.get(0)?.request_list?.let {
                                        if(it.size!=0){
                                            requestAdapter?.clear()
                                            requestAdapter?.addAll(it)
                                            requestAdapter?.notifyDataSetChanged()
                                        }else{
                                            Toast.makeText(context, "검색결과가 없습니다", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                            override fun onFailure(call: Call<Network>?, t: Throwable?) {

                            }
                        })
                    }


                }
            }



        }

        return convertView
    }

    override fun onResume() {
        super.onResume()
        var messagesCall = api?.requestContentsList(0)
        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                Log.v("requestContentsList : ", Gson().toJson(network))
                if(network?.message.equals("ok")){
                    network.data?.get(0)?.request_list?.let {
                        if(it.size!=0){
                            requestAdapter?.clear()
                            requestAdapter?.addAll(it)
                            requestAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Network>?, t: Throwable?) {

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0){
            var messagesCall = api?.requestContentsList(0)
            messagesCall?.enqueue(object : Callback<Network> {
                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    Log.v("requestContentsList : ", Gson().toJson(network))
                    if(network?.message.equals("ok")){
                        network.data?.get(0)?.request_list?.let {
                            if(it.size!=0){
                                requestAdapter?.clear()
                                requestAdapter?.addAll(it)
                                requestAdapter?.notifyDataSetChanged()
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<Network>?, t: Throwable?) {

                }
            })
        }
    }
}// Required empty public constructor
