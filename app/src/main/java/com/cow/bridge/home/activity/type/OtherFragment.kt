package com.cow.bridge.home.activity.type


import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.cow.bridge.R
import com.cow.bridge.home.adapter.OtherAdapter
import com.cow.bridge.home.dialog.OrderbyDialog
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import kotlinx.android.synthetic.main.fragment_other.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [OtherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OtherFragment : Fragment() {

    private var pageName: String? = null
    var api : ServerInterface? = null
    var otherAdapter : OtherAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            pageName = arguments!!.getString(pageNameParam)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val convertView = inflater!!.inflate(R.layout.fragment_other, container, false)
        api = ApplicationController.instance?.buildServerInterface()

        with(convertView){
            other_layout_orderby.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val orderbyDialog : OrderbyDialog = OrderbyDialog(activity!!, other_text_orderby.text.toString())
                    orderbyDialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                    orderbyDialog.show()
                    orderbyDialog.setOnDismissListener(object : DialogInterface.OnDismissListener{
                        override fun onDismiss(dialog: DialogInterface?) {
                            with(dialog as OrderbyDialog){
                                if(confirm){
                                    orderby?.let {
                                        other_text_orderby.text = it
                                        getContentsList(pageName, orderby)
                                    }
                                }
                            }
                        }

                    })
                }

            })

            otherAdapter = OtherAdapter(context)

            val llm : RecyclerView.LayoutManager = GridLayoutManager(context, 2)
            other_recycler.layoutManager = llm
            other_recycler.adapter = otherAdapter

            getContentsList(pageName,"Upload date")

        }

        return convertView
    }

    companion object {
        private var pageNameParam : String? = null

        fun newInstance(param1: String): OtherFragment {
            val fragment = OtherFragment()
            val args = Bundle()
            args.putString(pageNameParam, param1)
            fragment.arguments = args
            return fragment
        }
    }

    fun getContentsList(type : String?, orderby : String?, page : Int = 0){
        var messagesCall : Call<Network>? = null
        var category : Int = 0
        if(type.equals("k-content")){
            category = 1
        }else if(type.equals("k-pop")){
            category = 2
        }else if(type.equals("fun")){
            category = 3
        }else if(type.equals("culture")){
            category = 4
        }else{
            category = 0
        }

        if(orderby.equals("Upload date")){
            messagesCall = api?.recentContentsList(category, page)
        }else if(orderby.equals("View count")){
            messagesCall = api?.hitsortContentsList(category, page)
        }else if(orderby.equals("Rating")){
            messagesCall = api?.likesortContentsList(category, page)
        }else{
            messagesCall = api?.recentContentsList(category, page)
        }


        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                if(network?.message.equals("ok")){
                    network.data?.get(0)?.contents_list?.let {
                        if(it.size!=0){
                            if(page==0){
                                otherAdapter?.clear()
                            }
                            otherAdapter?.addAll(it)
                            otherAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Network>?, t: Throwable?) {

            }
        })
    }

}// Required empty public constructor
