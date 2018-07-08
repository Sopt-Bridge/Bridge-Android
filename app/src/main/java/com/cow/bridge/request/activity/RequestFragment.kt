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

import com.cow.bridge.R
import com.cow.bridge.request.adapter.RequestAdapter
import com.cow.bridge.util.RequestDividerItemDecoration
import com.cow.bridge.util.UtilController
import kotlinx.android.synthetic.main.fragment_request.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [RequestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RequestFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val convertView : View = inflater!!.inflate(R.layout.fragment_request, container, false)

        with(convertView){
            val requestAdapter = RequestAdapter(context)

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

            request_button_write.setOnClickListener{
                if(request_button_write.text.equals("write")){
                    var intent = Intent(context, RequestWriteActivity::class.java)
                    startActivity(intent)
                }else if(request_button_write.text.equals("search")){
                    //TODO 검색 api 연동
                }
            }
            //request_search.clearFocus()

        }

        return convertView
    }


}// Required empty public constructor
