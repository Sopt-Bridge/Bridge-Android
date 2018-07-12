package com.cow.bridge.login.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.cow.bridge.R
import com.cow.bridge.model.User
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginActivity : AppCompatActivity() {

    var callbackManager : CallbackManager? = null
    var mGoogleSignInClient : GoogleSignInClient? = null

    var api = ApplicationController.instance?.buildServerInterface()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //facebook 로그인 구현
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                Log.v("test", Profile.getCurrentProfile().id)
                var messagesCall = api?.login(User(Profile.getCurrentProfile().id.toString(), Profile.getCurrentProfile().name, 1))
                messagesCall?.enqueue(object : Callback<Network> {
                    override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                        var network = response!!.body()
                        Log.v("test", Gson().toJson(network))
                        if(network?.message.equals("Updated") || network?.message.equals("success")){
                            network.data?.get(0)?.userIdx?.let{
                                var sp : SharedPreferences = getSharedPreferences("bridge", MODE_PRIVATE)
                                var editor : SharedPreferences.Editor = sp.edit()
                                editor.putBoolean("login", true)
                                editor.putString("loginUuid", Profile.getCurrentProfile().id)
                                editor.putString("loginType", "facebook")
                                editor.putString("loginName", Profile.getCurrentProfile().name)
                                editor.putString("loginToken", network.data?.get(0)?.token)
                                editor.putInt("userIdx", network.data?.get(0)?.userIdx!!)
                                editor.commit()
                                //requestUserProfile(result!!)
                                Toast.makeText(applicationContext, "페이스북 로그인 성공", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginActivity, MypageActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                    override fun onFailure(call: Call<Network>?, t: Throwable?) {

                    }
                })



            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {
                Log.v("error : ", error.toString())
            }

        })

        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        if(isLoggedIn) {
            //Toast.makeText(applicationContext, "이미 페이스북 로그인이 되어있습니다", Toast.LENGTH_SHORT).show()
        }

        //google로그인 구현
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val account = GoogleSignIn.getLastSignedInAccount(this)
        account?.let {
            //Toast.makeText(applicationContext, "이미 구글 로그인이 되어있습니다", Toast.LENGTH_SHORT).show()
        }

        login_image_back.setOnClickListener {
            finish()
        }

        login_button_facebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        }

        login_button_google.setOnClickListener {
            val signInIntent = mGoogleSignInClient?.getSignInIntent();
            startActivityForResult(signInIntent, 57)

        }
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 57) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            account?.let {
                Log.v("test", account.id!!)
                var messagesCall = api?.login(User(account.id!!.toString(), account.displayName!!, 0))
                messagesCall?.enqueue(object : Callback<Network> {
                    override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                        var network = response!!.body()
                        Log.v("test", Gson().toJson(network))
                        if(network?.message.equals("Updated") || network?.message.equals("success")){
                            network.data?.get(0)?.userIdx?.let{
                                var sp : SharedPreferences = getSharedPreferences("bridge", MODE_PRIVATE)
                                var editor : SharedPreferences.Editor = sp.edit()
                                editor.putBoolean("login", true)
                                editor.putString("loginUuid", account.id)
                                editor.putString("loginType", "google")
                                editor.putString("loginName", account.displayName)
                                editor.putString("loginToken", network.data?.get(0)?.token)
                                editor.putInt("userIdx", network.data?.get(0)?.userIdx!!)
                                //editor.putString("loginEmail", account.email)
                                editor.commit()
                                Toast.makeText(applicationContext, account.displayName+ " 구글 로그인 성공", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginActivity, MypageActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                    override fun onFailure(call: Call<Network>?, t: Throwable?) {

                    }
                })

            }
        }else{
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    public fun requestUserProfile(loginResult : LoginResult){
        var request : GraphRequest = GraphRequest.newMeRequest(loginResult.accessToken, object : GraphRequest.GraphJSONObjectCallback{
            override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                var email = response?.jsonObject?.getString("email").toString()
                var sp : SharedPreferences = getSharedPreferences("bridge", MODE_PRIVATE)
                var editor : SharedPreferences.Editor = sp.edit()
                editor.putString("loginEmail", email)
                editor.commit()
            }
        })
        var parameters = Bundle()
        parameters.putString("fields", "email")
        request.parameters = parameters
        request.executeAsync()

    }

}
