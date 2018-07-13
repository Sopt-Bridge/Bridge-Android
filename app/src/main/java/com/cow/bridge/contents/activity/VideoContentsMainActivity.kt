package com.cow.bridge.contents.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import com.cow.bridge.R
import com.cow.bridge.contents.dialog.FeedBackDialog
import com.cow.bridge.model.Content
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_video_contents.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VideoContentsMainActivity :AppCompatActivity() {
    var api: ServerInterface? = ApplicationController.instance?.buildServerInterface()

    private var tablayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var btn_library: Button? = null
    var btn_feedback: Button? = null
    var txt_recommand: TextView? = null
    var btn_recommand: Button? = null
    var video_contents: WebView? = null
    var video_contents_mp4 : VideoView ?= null

    var txt_video_title: TextView? = null
    var txt_origin_url: TextView? = null
    var txt_hash: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_contents)
        api = ApplicationController.instance?.buildServerInterface()
        val intent = Intent(this.intent)
        val video: Content = intent.getSerializableExtra("videoContents") as Content

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
        video_contents_mp4 = video_contents_vv_video
        video_contents = video_contents_wv_video
        //others setting
        //video_contents?.start()

        txt_origin_url?.text = video.contentsUrl
        txt_recommand?.text = Integer.toString(video.contentsLike)
        txt_video_title?.text = video.contentsTitle

        var temp_hash = " "
        if(video.hashName1 != null) {
            temp_hash = video.hashName1
            if(video.hashName2 != null) {
                temp_hash = temp_hash + video.hashName2
                if(video.hashName3 != null) {
                    temp_hash = temp_hash + video.hashName3
                }
            }
        }
        txt_hash?.text = temp_hash

        if(video.contentsUrl.endsWith(".mp4")){
            video_contents_mp4?.visibility = View.VISIBLE
            video_contents?.visibility = View.INVISIBLE
            window.setFormat(PixelFormat.TRANSLUCENT)
            var url : Uri = Uri.parse(video.contentsUrl)
            var media : MediaController ?= MediaController(this)
            media?.setAnchorView(video_contents)
            //video_contents_mp4?.setVideoURI(url)
            video_contents_mp4?.setVideoPath(video.contentsUrl)
            media?.setMediaPlayer(video_contents_mp4)
            video_contents_mp4?.setMediaController(media)
            video_contents_mp4?.requestFocus()
            video_contents_mp4?.start()
        }
        else {
            video_contents_mp4?.visibility = View.INVISIBLE
            video_contents?.visibility = View.VISIBLE
            video_contents?.setWebViewClient(WebViewClient())
            var setting: WebSettings? = video_contents?.settings
            var webClient: WebChromeClient = WebChromeClient()
            var client: WebViewClient = WebViewClient()
            video_contents?.webChromeClient = webClient
            video_contents?.webViewClient = client
            setting!!.javaScriptEnabled = true
            setting!!.mediaPlaybackRequiresUserGesture = false
            video_contents?.settings?.pluginState = WebSettings.PluginState.ON
            video_contents?.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            video_contents?.isHorizontalScrollBarEnabled = false
            video_contents?.isVerticalScrollBarEnabled = false
            setting.builtInZoomControls = true
            var url: String = "<iframe width=\"360dp\" height=\"186.95dp\" \" src=\"" + video.contentsUrl + "\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>\n"
            video_contents?.loadDataWithBaseURL("file:///android_asset/", url, "text/html", "utf-8", null)
        }


        //video_contents?.loadUrl(url)



        //initialize
        //좋아요버튼
        if (video.likeFlag == 1)
            btn_recommand?.setBackgroundResource(R.drawable.good_active_icon)
        else
            btn_recommand?.setBackgroundResource(R.drawable.good_normal_btn)
        //library여부
        if (video.subFlag == 1)
            btn_library?.setBackgroundResource(R.drawable.add_to_library_active_icon)
        else
            btn_library?.setBackgroundResource(R.drawable.add_to_library_normal_icon)

        btn_recommand?.setOnClickListener {

            var messagesCall = api?.clikeContents(Content(video?.contentsIdx!!, 1))
            messagesCall?.enqueue(object : Callback<Network> {
                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    if (network?.message.equals("ok")) {
                        if (video.likeFlag == 1) {
                            btn_recommand?.setBackgroundResource(R.drawable.good_normal_btn)
                            video.contentsLike--
                            video.likeFlag = 0
                            txt_recommand?.text = Integer.toString(video.contentsLike)

                        } else {
                            btn_recommand?.setBackgroundResource(R.drawable.good_active_icon)
                            video.contentsLike++
                            video.likeFlag = 1
                            txt_recommand?.text = Integer.toString(video.contentsLike)
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

        video_contents_bt_feedback.setOnClickListener {
            val feedbackDialog : FeedBackDialog = FeedBackDialog(this@VideoContentsMainActivity, video?.contentsIdx!!)
            feedbackDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            feedbackDialog.show()
        }
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
