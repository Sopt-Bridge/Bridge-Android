package com.cow.bridge.home.activity.type.hot


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cow.bridge.R


/**
 * A simple [Fragment] subclass.
 */
class NowTrendFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_now_trend, container, false)
    }

}// Required empty public constructor
