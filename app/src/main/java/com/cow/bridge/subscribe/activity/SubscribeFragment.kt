package com.cow.bridge.subscribe.activity


import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cow.bridge.R
import com.cow.bridge.R.id.subscribe_layout_orderby
import com.cow.bridge.R.id.subscribe_text_orderby
import com.cow.bridge.home.dialog.OrderbyDialog
import com.cow.bridge.subscribe.adapter.MySubscribeAdapter
import com.cow.bridge.subscribe.adapter.SubscribeContentAdapter
import kotlinx.android.synthetic.main.fragment_subscribe.*
import kotlinx.android.synthetic.main.fragment_subscribe.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [SubscribeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubscribeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var convertView = inflater!!.inflate(R.layout.fragment_subscribe, container, false)

        with(convertView){
            subscribe_layout_orderby.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val orderbyDialog : OrderbyDialog = OrderbyDialog(activity!!, subscribe_text_orderby.text.toString())
                    orderbyDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    orderbyDialog.show()
                    orderbyDialog.setOnDismissListener(object : DialogInterface.OnDismissListener{
                        override fun onDismiss(dialog: DialogInterface?) {
                            with(dialog as OrderbyDialog){
                                orderby?.let {
                                    convertView.subscribe_text_orderby.text = it
                                    //TODO : 해당 정렬순으로 리스트가져오기
                                }
                            }
                        }

                    })
                }

            })

            val mylistAdapter : MySubscribeAdapter = MySubscribeAdapter(context)
            val subscribeContentAdapter : SubscribeContentAdapter = SubscribeContentAdapter(context)

            val llm : LinearLayoutManager = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.HORIZONTAL
            subscribe_recycler_mylist.layoutManager = llm
            subscribe_recycler_mylist.adapter = mylistAdapter

            val llm2 : RecyclerView.LayoutManager = GridLayoutManager(context, 2)
            subscribe_recycler.layoutManager = llm2
            subscribe_recycler.adapter = subscribeContentAdapter

            subscribe_text_more.setOnClickListener{
                val intent = Intent(context, BestChannelActivity::class.java)
                intent.putExtra("title", "My Subscriptions")
                startActivity(intent)

            }

        }

        return convertView
    }


}// Required empty public constructor
