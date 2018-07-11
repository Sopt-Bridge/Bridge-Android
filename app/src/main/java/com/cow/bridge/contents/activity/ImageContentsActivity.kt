package com.cow.bridge.contents.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.cow.bridge.R
import com.cow.bridge.model.Content
import kotlinx.android.synthetic.main.activity_main.view.*

class ImageContentsActivity : AppCompatActivity() {
    private lateinit var cancelButton : ImageButton

    lateinit var imgPager: ViewPager

    var clickId : ArrayList<View> = ArrayList()

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
        val text7 : TextView = findViewById(R.id.imgCredit)
        var text8 : TextView = findViewById(R.id.currentNum)
        val text9 : TextView = findViewById(R.id.slash)

        clickId.add(text1)
        clickId.add(text2)
        clickId.add(text3)
        clickId.add(text4)
        clickId.add(text5)
        clickId.add(text6)
        clickId.add(text7)
        clickId.add(text8)
        clickId.add(text9)

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

        Log.d("imageContents",imageContents.toString())

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



        // view pager 연결
        val adapter = ViewPagerAdapter(supportFragmentManager)
        for (i in 1..imageContents?.imgCnt!!)  {
            adapter.addFragment(ImageFragment.newInstance(i,imageContents.contentsIdx))
        }
        imgPager.offscreenPageLimit = 2
        imgPager.adapter = adapter



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
