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
import kotlinx.android.synthetic.main.fragment_other.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [OtherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OtherFragment : Fragment() {

    private var pageName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            pageName = arguments!!.getString(pageNameParam)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val convertView = inflater!!.inflate(R.layout.fragment_other, container, false)

        with(convertView){
            other_layout_orderby.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val orderbyDialog : OrderbyDialog = OrderbyDialog(activity!!, other_text_orderby.text.toString())
                    orderbyDialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                    orderbyDialog.show()
                    orderbyDialog.setOnDismissListener(object : DialogInterface.OnDismissListener{
                        override fun onDismiss(dialog: DialogInterface?) {
                            with(dialog as OrderbyDialog){
                                orderby?.let {
                                    other_text_orderby.text = it
                                    //TODO : 해당 정렬순으로 리스트가져오기
                                }
                            }
                        }

                    })
                }

            })

            val otherAdapter : OtherAdapter = OtherAdapter(context)

            val llm : RecyclerView.LayoutManager = GridLayoutManager(context, 2)
            other_recycler.layoutManager = llm
            other_recycler.adapter = otherAdapter

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

}// Required empty public constructor
