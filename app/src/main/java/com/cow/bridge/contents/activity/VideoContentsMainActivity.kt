package com.cow.bridge.contents.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.cow.bridge.R
import com.cow.bridge.model.Content
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.ServerInterface
import kotlinx.android.synthetic.main.activity_video_contents.*
import kotlinx.android.synthetic.main.row_contents_simple.view.*

class VideoContentsMainActivity :AppCompatActivity(){
    val intent = Intent(this.intent)
    val video :Content= intent.getSerializableExtra("videoContents") as Video
    val api : ServerInterface? = ApplicationController.instance?.buildServerInterface()

    private var tablayout : TabLayout?= null
    var viewPager : ViewPager?= null
    var btn_library : Button ?= null
    var btn_feedback : Button ?= null
    var txt_recommand : TextView ?= null
    var btn_recommand : Button ?= null

    var txt_title : TextView ?= null
    var txt_credit : TextView ?= null
    var txt_hash : TextView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_contents)
        //tab
        tablayout = video_contents_tab
        viewPager = video_contents_vp_viewpager
        tablayout!!.setupWithViewPager(viewPager)
        setupViewPager(viewPager)

        //others
        txt_hash = video_contents_tv_hash
        txt_title = video_contents_tv_title
        txt_credit = video_contents_tv_credit
        btn_library = video_contents_bt_library
        btn_feedback = video_contents_bt_feedback
        txt_recommand = video_contents_tv_recommand
        btn_recommand = video_contents_bt_recommend

        txt_title.text = video.contentsTitle
        txt_hash.text = video.hashName1 + video.hashName2 + video.hashName3
        txt_credit.text = video.contentsInfo
        txt_recommand.text = video.contentsLike

        btn_recommand.setOnClickListener {
            if (video.likeFlag == 1) {
                btn_recommand.setImageResource(R.drawable.good_normal_btn)
                video.contents--
            } else {
                btn_recommand.setImageResource(R.drawable.good_active_icon)
                video.contentsLike++
            }
        }
        btn_library

    }
    init(){
        //좋아요 버튼
        if(video.likeFlag == 1)
            btn_recommand.setImageResource(R.drawable.good_active_icon)
        else
            btn_recommand.setImageResource(R.drawable.good_normal_btn)
        //library여부
        if(video.subFlag == 1)
            btn_library.setImageResource(R.drawable.add_to_library_active_icon)
        else
            btn_library.setImageResource(R.drawable.add_to_library_normal_icon)
    }



    private fun setupViewPager(viewPager: ViewPager?){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(VideoContentsCommentFragment(), "댓글")
        adapter.addFragment(VideoContentsVideoFragment(), "영상")
        viewPager?.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager : FragmentManager) : FragmentPagerAdapter(manager){
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position : Int) : Fragment{
            return mFragmentList[position]
        }
        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }
}
