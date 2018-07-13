package com.cow.bridge.login.activity

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.cow.bridge.R
import com.cow.bridge.login.adapter.MypageAdapter
import com.cow.bridge.model.MypageItems
import com.cow.bridge.model.User
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.util.UtilController
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import kotlinx.android.synthetic.main.activity_mypage.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MypageActivity : AppCompatActivity() {

    lateinit var myAdapter: MypageAdapter
    private lateinit var cancelButton : ImageButton
    var powerMenu : PowerMenu? = null
    var api = ApplicationController.instance?.buildServerInterface()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        val recyclerView = findViewById<View>(R.id.mypage_recycler_mywritten) as RecyclerView

        var sp = getSharedPreferences("bridge", Context.MODE_PRIVATE)
        mypage_text_name.text = sp.getString("loginName", "")

        myAdapter = MypageAdapter(this@MypageActivity)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter
        recyclerView.addItemDecoration(ItemDecoration())

        var messagesCall = api?.getMyTextList(sp.getInt("userIdx", 0))
        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                if(network?.message.equals("ok")){
                    network.data?.get(0)?.request_list?.let {
                        if(it.size!=0){
                            myAdapter.addAll(it)
                            myAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Network>?, t: Throwable?) {

            }
        })

        powerMenu = PowerMenu.Builder(applicationContext)
                .addItem(PowerMenuItem("Sign out", false))
                .addItem(PowerMenuItem("Remove this account", false))
                .setDividerHeight(UtilController.convertDpToPixel(1f, applicationContext).toInt())
                .setDivider(resources.getDrawable(R.drawable.line_rect_1dp_e4e4e4))
                .setAnimation(MenuAnimation.ELASTIC_TOP_RIGHT)
                .setWith(UtilController.convertDpToPixel(150f, applicationContext).toInt())
                .setMenuRadius(16f)
                .setMenuShadow(8f)
                .setTextColor(Color.parseColor("#333333"))
                .setMenuColor(Color.parseColor("#FFFFFF"))
                .setOnMenuItemClickListener(onMenuItemClickListener())
                .build()

        cancelButton = findViewById(R.id.mypage_imagebutton_back)
        cancelButton.setOnClickListener{finish()}

        mypage_imagebutton_option.setOnClickListener {
            powerMenu?.showAsDropDown(mypage_imagebutton_option, UtilController.convertDpToPixel(15f, applicationContext).toInt(), 0)
        }
    }

    private fun onMenuItemClickListener() = OnMenuItemClickListener<PowerMenuItem>(){ position: Int, powerMenuItem: PowerMenuItem ->
        if(position==0){
            var sp = getSharedPreferences("bridge", Context.MODE_PRIVATE)
            var editor : SharedPreferences.Editor = sp.edit()
            editor.putBoolean("login", false)
            editor.putString("loginType", "")
            editor.putString("loginName", "")
            editor.putString("loginEmail", "")
            editor.commit()
            Toast.makeText(applicationContext, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            var sp = getSharedPreferences("bridge", Context.MODE_PRIVATE)
            var loginType = sp.getString("loginType", "")
            if(loginType.equals("facebook")){
                var messagesCall = api?.withdrawal(User(sp.getString("loginUuid", "")))
                messagesCall?.enqueue(object : Callback<Network>{
                    override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                        var network = response!!.body()
                        Log.v("test", Gson().toJson(network))
                        if(network?.message.equals("ok")){
                            LoginManager.getInstance().logOut()
                            Toast.makeText(applicationContext, "탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                    override fun onFailure(call: Call<Network>?, t: Throwable?) {
                        Log.v("test", t.toString())
                    }
                })

            }else if(loginType.equals("google")){
                var messagesCall = api?.withdrawal(User(sp.getString("loginToken", "")))
                messagesCall?.enqueue(object : Callback<Network>{
                    override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                        var network = response!!.body()
                        Log.v("test", Gson().toJson(network))
                        if(network?.message.equals("ok")){
                            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
                            var mGoogleSignInClient = GoogleSignIn.getClient(this@MypageActivity, gso);
                            mGoogleSignInClient.signOut().addOnCompleteListener {
                                Toast.makeText(applicationContext, "탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                    }
                    override fun onFailure(call: Call<Network>?, t: Throwable?) {
                        Log.v("test", t.toString())
                    }
                })

            }
        }
        powerMenu?.dismiss()

    }
}
