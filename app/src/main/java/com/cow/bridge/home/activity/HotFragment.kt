package com.cow.bridge.home.activity


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cow.bridge.R


/**
 * A simple [Fragment] subclass.
 */
class HotFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val convertView = inflater!!.inflate(R.layout.fragment_hot, container, false)



        return convertView
    }

}// Required empty public constructor
