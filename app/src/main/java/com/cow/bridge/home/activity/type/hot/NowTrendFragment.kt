package com.cow.bridge.home.activity.type.hot


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.cow.bridge.R
import com.cow.bridge.model.Content
import com.cow.bridge.network.ApplicationController
import kotlinx.android.synthetic.main.fragment_now_trend.view.*


/**
 * A simple [Fragment] subclass.
 */
class NowTrendFragment : Fragment() {

    private var content: Content? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            content = arguments!!.getSerializable("contents") as Content
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val convertView = inflater.inflate(R.layout.fragment_now_trend, container, false)

        with(convertView){
            nowtrend_text_title.text = content?.contentsTitle
            if(content?.contentsType==0){
                Glide.with(context).load(ApplicationController.imageUrl(content?.contentsIdx!!, 1)).into(nowtrend_image_thumbnail)
            }else{
                Glide.with(context).load(ApplicationController.videoThumbnailUrl(content?.contentsIdx!!)).into(nowtrend_image_thumbnail)
            }
            nowtrend_image_thumbnail
        }

        return convertView
    }


    companion object {

        fun newInstance(param1: Content): NowTrendFragment {
            val fragment = NowTrendFragment()
            val args = Bundle()
            args.putSerializable("contents", param1)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
