package com.cow.bridge.contents.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.cow.bridge.R
import com.cow.bridge.contents.adapter.ImageCommentAdapter
import com.cow.bridge.model.Content
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_image_contents.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageContentsActivity : AppCompatActivity() {
    private lateinit var cancelButton : ImageButton
    lateinit var imgPager: ViewPager

    var clickId : ArrayList<View> = ArrayList()

    val api : ServerInterface? = ApplicationController.instance?.buildServerInterface()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_contents)

        imgPager = findViewById(R.id.viewPager)
        cancelButton = findViewById(R.id.imgContentsBackBtn)
        cancelButton.setOnClickListener{finish()}



        // 화면 터치 시 아이콘 보임, 안 보임
        val text1 : TextView = findViewById(R.id.imgCount)
        val text2 : ImageButton = findViewById(R.id.imgLibraryBtn)
        val text3 : ImageButton = findViewById(R.id.imgFeedbackBtn)
        val text4 : TextView = findViewById(R.id.imgDes)
        val text5 : ImageButton = findViewById(R.id.imgLike)
        val text6 : TextView = findViewById(R.id.imgLikeNum)
        val text7 : TextView = findViewById(R.id.currentNum)
        val text8 : TextView = findViewById(R.id.slash)

        clickId.add(text1)
        clickId.add(text2)
        clickId.add(text3)
        clickId.add(text4)
        clickId.add(text5)
        clickId.add(text6)
        clickId.add(text7)
        clickId.add(text8)

        val container : LinearLayout = findViewById(R.id.imgMain)
        container.setOnClickListener {
            for(s in clickId) {
                if(s.visibility == View.VISIBLE) {
                    s.visibility = View.INVISIBLE
                }
                else s.visibility = View.VISIBLE
            }
        }

        // imgCnt, imgDes, imgLikeNum 부분
        var intent = Intent(this.intent)
        var imageContents= intent.getSerializableExtra("imageContents") as? Content

        text1.text = imageContents?.imgCnt.toString()
        text4.text = imageContents?.contentsInfo
        text6.text = imageContents?.contentsLike.toString()

        //imgLike 아이콘 부분
        var likeFlag : Int?
        likeFlag = imageContents?.likeFlag

        if(likeFlag == 0) {
            text5.setBackgroundResource(R.drawable.good_normal_btn)
        }
        if (likeFlag == 1) {
            text5.setBackgroundResource(R.drawable.good_active_icon)
        }

        text5.setOnClickListener {
            var messagesCall = api?.clikeContents(Content(imageContents?.contentsIdx!! , 1))
            messagesCall?.enqueue(object : Callback<Network> {
                override fun onFailure(call: Call<Network>?, t: Throwable?) {
                    Log.v("test fail : ", t.toString())
                }
                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    Log.v("test", Gson().toJson(network))
                    if(network?.message.equals("ok")) {
                        if(likeFlag==0){
                            likeFlag =1
                            text5.setBackgroundResource(R.drawable.good_normal_btn)
                        } else if(likeFlag==0){
                            likeFlag =0
                            text5.setBackgroundResource(R.drawable.good_active_icon)
                        }
                    }
                }
            })
        }


        // view pager 연결
        val adapter = ViewPagerAdapter(supportFragmentManager)
        for (i in 1..imageContents?.imgCnt!!)  {
            adapter.addFragment(ImageFragment.newInstance(i,imageContents.contentsIdx))
        }

        imgPager.offscreenPageLimit = 2
        imgPager.adapter = adapter

        imgPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                text7.text = position.plus(1).toString()
                Log.v("test : ", "$position")
            }

        })

        val imgCommentAdapter: ImageCommentAdapter = ImageCommentAdapter(applicationContext)

        val llm: LinearLayoutManager = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        image_comments_list.layoutManager = llm
        image_comments_list.adapter = imgCommentAdapter


        var messagesCall = api?.getImageContentCommentList(13, 0)
        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                Log.v("imageCommentList : ", Gson().toJson(network))
                if (network?.message.equals("ok")) {
                    network.data?.get(0)?.comments_list?.let {
                        if (it.size != 0) {
                            imgCommentAdapter.addAll(it)
                            imgCommentAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Network>?, t: Throwable?) {
                Log.v("test fail : ", t.toString())
            }
        })

    }

    inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment) {
            mFragmentList.add(fragment)
        }
    }


    override fun onStart() {
        super.onStart()

    }



}
