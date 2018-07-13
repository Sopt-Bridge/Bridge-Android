package com.cow.bridge.contents.activity

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.*
import com.cow.bridge.R
import com.cow.bridge.contents.adapter.ImageContentsCommentAdapter
import com.cow.bridge.contents.dialog.FeedBackDialog
import com.cow.bridge.library.dialog.LibraryAddContentsDialog
import com.cow.bridge.model.Content
import com.cow.bridge.model.ContentsComment
import com.cow.bridge.model.Group
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_image_contents.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ImageContentsActivity : AppCompatActivity() {
    private lateinit var cancelButton: ImageButton
    lateinit var imgPager: ViewPager

    var clickId: ArrayList<View> = ArrayList()

    val api: ServerInterface? = ApplicationController.instance?.buildServerInterface()
    var imgCommentAdapter: ImageContentsCommentAdapter? = null
    var imageContents : Content? = null
    var likeFlag: Int = 0
    var libraryFlag: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_contents)

        imgPager = findViewById(R.id.viewPager)
        cancelButton = findViewById(R.id.imgContentsBackBtn)
        cancelButton.setOnClickListener{finish()}

        // 화면 터치 시 아이콘 보임, 안 보임
        val text1: TextView = findViewById(R.id.imgCount)
        val text2: ImageButton = findViewById(R.id.imgLibraryBtn)
        val text3: ImageButton = findViewById(R.id.imgFeedbackBtn)
        val text4: TextView = findViewById(R.id.imgDes)
        val text5: ImageButton = findViewById(R.id.imgLike)
        val text6: TextView = findViewById(R.id.imgLikeNum)
        val text7: TextView = findViewById(R.id.currentNum)
        val text8: TextView = findViewById(R.id.slash)
        val text9: Button = findViewById(R.id.image_contents_comment_post_btn)

        clickId.add(text1)
        clickId.add(text2)
        clickId.add(text3)
        clickId.add(text4)
        clickId.add(text5)
        clickId.add(text6)
        clickId.add(text7)
        clickId.add(text8)
        clickId.add(text9)

        val container: LinearLayout = findViewById(R.id.imgMain)
        container.setOnClickListener {
            for (s in clickId) {
                if (s.visibility == View.VISIBLE) {
                    s.visibility = View.INVISIBLE
                } else s.visibility = View.VISIBLE
            }
        }

        // imgCnt, imgDes, imgLikeNum 부분
        var intent = Intent(this.intent)
        imageContents = intent.getSerializableExtra("imageContents") as? Content

        var sp : SharedPreferences = getSharedPreferences("bridge", MODE_PRIVATE)
        var myUserIdx = sp.getInt("userIdx", 0)
        Log.v("testtttt", imageContents?.contentsIdx.toString()!!)
        Log.v("testtttt", myUserIdx.toString())
        var messagesCall = api?.getContents(Content(imageContents?.contentsIdx!!, myUserIdx, 0))
        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                Log.v("test", Gson().toJson(network))
                if(network?.message.equals("ok")){
                    network.data?.get(0)?.contents_list?.let {
                        if(it.size!=0){

                            //imgLike 아이콘 부분
                            likeFlag = it[0].likeFlag

                            if (likeFlag == 0) {
                                text5.setBackgroundResource(R.drawable.good_normal_btn)
                            }
                            if (likeFlag == 1) {
                                text5.setBackgroundResource(R.drawable.good_active_icon)
                            }


                            libraryFlag = it[0].subFlag

                            if (libraryFlag == 0) {
                                text2.setBackgroundResource(R.drawable.add_to_library_normal_icon)
                            }
                            if (libraryFlag == 1) {
                                text2.setBackgroundResource(R.drawable.add_to_library_active_icon)
                            }

                        }
                    }
                }
            }
            override fun onFailure(call: Call<Network>?, t: Throwable?) {

            }
        })


        text1.text = imageContents?.imgCnt.toString()
        text4.text = imageContents?.contentsInfo
        text6.text = imageContents?.contentsLike.toString()


        text5.setOnClickListener {
            var sp : SharedPreferences = getSharedPreferences("bridge", MODE_PRIVATE)
            var myUserIdx = sp.getInt("userIdx", 0)
            var messagesCall = api?.clikeContents(Content(imageContents?.contentsIdx!!, myUserIdx))
            messagesCall?.enqueue(object : Callback<Network> {
                override fun onFailure(call: Call<Network>?, t: Throwable?) {
                    Log.v("test fail : ", t.toString())
                }

                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    if(network?.message.equals("ok")) {
                        if(imageContents?.likeFlag==1){
                            text5.setBackgroundResource(R.drawable.good_normal_btn)
                            imageContents!!.contentsLike--
                            imageContents?.likeFlag =0
                            text6.text = Integer.toString(imageContents?.contentsLike!!)
                        } else {
                            text5.setBackgroundResource(R.drawable.good_active_icon)
                            imageContents!!.contentsLike++
                            imageContents?.likeFlag =1
                            text6.text = Integer.toString(imageContents?.contentsLike!!)

                        }
                    }
                }
            })
        }



        text2.setOnClickListener {
            if (libraryFlag == 0) {
                val libraryAddContentsDialog : LibraryAddContentsDialog = LibraryAddContentsDialog(this@ImageContentsActivity, imageContents?.contentsIdx!!)
                libraryAddContentsDialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                libraryAddContentsDialog.show()
                libraryAddContentsDialog.setOnDismissListener(object : DialogInterface.OnDismissListener{
                    override fun onDismiss(dialog: DialogInterface?) {
                        with(dialog as LibraryAddContentsDialog){
                            if(addContents){
                                libraryFlag = 1
                                text2.setBackgroundResource(R.drawable.add_to_library_active_icon)
                            }
                        }
                    }

                })

            }
            if (libraryFlag == 1) {
                var group = Group()
                group.contentsIdx = imageContents?.contentsIdx!!
                var messagesCall = api?.deleteGroupGontents(group)
                messagesCall?.enqueue(object : Callback<Network> {
                    override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                        var network = response!!.body()
                        Log.v("deleteGroupGontents", Gson().toJson(network))
                        if(network?.message.equals("ok")){
                            libraryFlag = 0
                            text2.setBackgroundResource(R.drawable.add_to_library_normal_icon)
                        }
                    }
                    override fun onFailure(call: Call<Network>?, t: Throwable?) {

                    }
                })
            }
        }

        // FeedBack Dialog
        text3.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val feedbackDialog : FeedBackDialog = FeedBackDialog(this@ImageContentsActivity, imageContents?.contentsIdx!!)
                feedbackDialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                feedbackDialog.show()
            }

        })

        //comment post
        text9.setOnClickListener {
            var commentTmp = ContentsComment()

            if (contents_comment_edit.text.toString().trim().equals("")) {
                Toast.makeText(applicationContext, "enter the comment", Toast.LENGTH_SHORT).show()
            } else {
                commentTmp.ccmtContent = (contents_comment_edit.text.toString())
                commentTmp.contentsIdx = imageContents?.contentsIdx!!
                var sp : SharedPreferences = getSharedPreferences("bridge", MODE_PRIVATE)
                var myUserIdx = sp.getInt("userIdx", 0)
                commentTmp.userIdx = myUserIdx

                var messagesCall = api?.contentsCommentWrite(commentTmp)
                messagesCall?.enqueue(object : Callback<Network> {
                    override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                        var network = response!!.body()
                        Log.v("contentsCommentWrite", Gson().toJson(network))
                        if (network?.message.equals("ok")) {
                            contents_comment_edit.setText("")
                            getContentsCommentList()
                        } else {
                            Toast.makeText(applicationContext, "error : ${network?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Network>?, t: Throwable?) {
                        Toast.makeText(applicationContext, "error : ${t.toString()}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }



        // view pager 연결
        val adapter = ViewPagerAdapter(supportFragmentManager)
        for (i in 1..imageContents?.imgCnt!!) {
            adapter.addFragment(ImageFragment.newInstance(i, imageContents?.contentsIdx!!))
        }
        imgPager.offscreenPageLimit = 2
        imgPager.adapter = adapter

        imgPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                text7.text = position.plus(1).toString()
            }

        })

        imgCommentAdapter = ImageContentsCommentAdapter(this@ImageContentsActivity)

        val llm: LinearLayoutManager = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        image_comments_list.layoutManager = llm
        image_comments_list.adapter = imgCommentAdapter

        getContentsCommentList()
    }


    public fun getContentsCommentList(){
        var messagesCall = api?.getContentCommentList(imageContents?.contentsIdx!!, 0)

        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                if (network?.message.equals("ok")) {
                    network.data?.get(0)?.comments_list?.let {
                        if (it.size != 0) {
                            imgCommentAdapter?.clear()
                            imgCommentAdapter?.addAll(it)
                            imgCommentAdapter?.notifyDataSetChanged()
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
