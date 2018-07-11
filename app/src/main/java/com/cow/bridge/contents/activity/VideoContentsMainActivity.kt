package com.cow.bridge.contents.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import com.cow.bridge.R
import com.cow.bridge.model.Content
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.ServerInterface
import kotlinx.android.synthetic.main.activity_video_contents.*
import kotlinx.android.synthetic.main.row_contents_simple.view.*
import retrofit2.*
import com.cow.bridge.network.Network
import com.google.gson.Gson




class VideoContentsMainActivity :AppCompatActivity() {
    //val intent = Intent(this.intent)
    val video: Content = intent.getSerializableExtra("videoContents") as Content
    val api: ServerInterface? = ApplicationController.instance?.buildServerInterface()

    private var tablayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var btn_library: Button? = null
    var btn_feedback: Button? = null
    var txt_recommand: TextView? = null
    var btn_recommand: Button? = null
    var video_contents: VideoView? = null

    var txt_video_title: TextView? = null
    var txt_origin_url: TextView? = null
    var txt_hash: TextView? = null

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
        txt_video_title = video_contents_tv_title
        txt_origin_url = video_contents_tv_contents_origin_url
        btn_library = video_contents_bt_library
        btn_feedback = video_contents_bt_feedback
        txt_recommand = video_contents_tv_recommand
        btn_recommand = video_contents_bt_recommend
        video_contents = video_contents_vv_video
        //others setting

        txt_origin_url?.text = video.contentsTitle
        txt_hash?.text = video.hashName1 + " " + video.hashName2 + " " + video.hashName3
        txt_recommand?.text = Integer.toString(video.contentsLike)
        txt_video_title?.text = video.contentsTitle
        video_contents?.setVideoURI(Uri.parse(video.contentsUrl))

        btn_recommand?.setOnClickListener {

            var messagesCall = api?.changeVideoContentsLike(video.contentsIdx, 1)
            messagesCall?.enqueue(object : Callback<Network> {
                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    Log.v("getHashContentList : ", Gson().toJson(network))
                    if (network?.message.equals("ok")) {
                        if (video.likeFlag == 1) {
                            btn_recommand?.setBackgroundResource(R.drawable.good_normal_btn)
                            video.contentsLike--
                            video.likeFlag = 0
                        } else {
                            btn_recommand?.setBackgroundResource(R.drawable.good_active_icon)
                            video.contentsLike++
                            video.likeFlag = 1
                        }
                    }
                }
                override fun onFailure(call: Call<Network>?, t: Throwable?) {

                }
            })
        }
        btn_library?.setOnClickListener {
            if (video.subFlag == 1) {
                btn_library?.setBackgroundResource(R.drawable.add_to_library_normal_icon)
                video.subFlag = 0
            } else {
                btn_library?.setBackgroundResource(R.drawable.add_to_library_active_icon)
                video.subFlag = 1
            }
        }
    }

    init {
        //좋아요 버튼
        if (video.likeFlag == 1)
            btn_recommand?.setBackgroundResource(R.drawable.good_active_icon)
        else
            btn_recommand?.setBackgroundResource(R.drawable.good_normal_btn)
        //library여부
        if (video.subFlag == 1)
            btn_library?.setBackgroundResource(R.drawable.add_to_library_active_icon)
        else
            btn_library?.setBackgroundResource(R.drawable.add_to_library_normal_icon)
    }

    fun changeVideoContentsLike(contentsIdx: Int, userIdx: Int) {

    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(VideoContentsCommentFragment(), "댓글")
        adapter.addFragment(VideoContentsVideoFragment(), "영상")
        viewPager?.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
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
