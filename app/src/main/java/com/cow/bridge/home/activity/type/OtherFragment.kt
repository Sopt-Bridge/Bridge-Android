package com.cow.bridge.home.activity.type


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cow.bridge.R


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
            pageName = arguments.getString(pageNameParam)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val convertView = inflater!!.inflate(R.layout.fragment_other, container, false)



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
