package com.cow.bridge.contents.activity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.cow.bridge.R
import com.cow.bridge.network.ApplicationController
import kotlinx.android.synthetic.main.fragment_image_contents.view.*

class ImageFragment : Fragment() {


    private var imgNumber: Int? = null
    private var contentsIdx : Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null) {
            imgNumber = getArguments()?.getInt("imgNumber")
            contentsIdx = getArguments()?.getInt("contentsIdx")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = View.inflate(context!!,R.layout.fragment_image_contents,null)
        Glide.with(this).load(ApplicationController.imageUrl(contentsIdx!!, imgNumber!!)).into(view.imgDetail)
        return view
    }

    companion object {
        fun newInstance(param1 : Int, param2 : Int) : ImageFragment {
            val fragment = ImageFragment()
            val args = Bundle()
            args.putInt("imgNumber", param1)
            args.putInt("contentsIdx", param2)
            fragment.arguments = args
            return fragment
        }
    }

}