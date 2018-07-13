package com.cow.bridge.search.activity

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cow.bridge.R
import com.cow.bridge.home.adapter.SearchResultAdapter
import com.cow.bridge.model.Hash
import com.cow.bridge.model.SearchWord
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.search.searchlibrary.MaterialSearchView
import com.cow.bridge.util.UtilController
import com.google.gson.Gson
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SearchActivity : AppCompatActivity() {

    var searchView : MaterialSearchView? = null
    var api = ApplicationController.instance?.buildServerInterface()
    var searchResultAdapter : SearchResultAdapter? = null
    var queryTmp = ""
    var hashSubFlag = 0
    var powerMenu : PowerMenu? = null
    var searchFlag : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchView = findViewById(R.id.search_view)

        searchView?.setVoiceSearch(false)
        searchView?.setSubmitOnClick(true)
        searchView?.setCursorDrawable(R.drawable.color_cursor_white)

        searchView?.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                searchView?.hidePreview()
                searchView?.hideKeyboard(searchView!!)

                //TODO 썸네일 중복 제거
                searchView?.setLayoutParams(FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, UtilController.convertDpToPixel(48f, applicationContext).toInt()));
                queryTmp = query!!
                if(searchView?.searchType.equals("normal")){
                    var realm = Realm.getDefaultInstance()
                    realm.executeTransaction {
                        var searchWord = realm.createObject(SearchWord::class.java)
                        searchWord.recentlyWord = query
                        searchWord.searchDateTime = Date().toString()
                    }
                    realm.close()
                    getSearchContentsList(query!!, 1, 0)
                }else{
                    searchFlag = true

                    getSearchContentsList(if(query.startsWith("#")) query.substring(1) else query, 0, 0)
                }

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }


        })

        searchView?.showSearch(false)

        searchView?.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener{
            override fun onSearchVIewFocus() {
                searchView?.setLayoutParams(FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

            }

            override fun onSearchViewBack() {
                finish()
            }

            override fun onSearchViewClosed() {


            }

            override fun onSearchViewShown() {


            }

        })

        var realm  = Realm.getDefaultInstance()
        var normalRecentlyWords : RealmResults<SearchWord> = realm.where(SearchWord::class.java).findAll();
        var searchName : Array<String?> = arrayOfNulls<String>(normalRecentlyWords.size)
        var count = 0
        for(word in normalRecentlyWords){
            searchName[count++] = word.recentlyWord!!
        }

        realm.close()

        searchView?.setSuggestions(searchName);

        searchResultAdapter = SearchResultAdapter(this)

        val llm : LinearLayoutManager = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        search_recycler_result.layoutManager = llm
        search_recycler_result.adapter = searchResultAdapter

        powerMenu = PowerMenu.Builder(this@SearchActivity)
                .addItem(PowerMenuItem("Upload date", false))
                .addItem(PowerMenuItem("View count", false))
                .addItem(PowerMenuItem("Rating", false))
                .setDividerHeight(UtilController.convertDpToPixel(1f, this@SearchActivity).toInt())
                .setDivider(resources.getDrawable(R.drawable.line_rect_1dp_e4e4e4))
                .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
                .setWith(UtilController.convertDpToPixel(150f, this@SearchActivity).toInt())
                .setMenuRadius(16f)
                .setMenuShadow(8f)
                .setTextColor(Color.parseColor("#333333"))
                .setMenuColor(Color.parseColor("#FFFFFF"))
                .setOnMenuItemClickListener(onMenuItemClickListener())
                .build()

        search_layout_orderby.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                powerMenu?.showAsDropDown(search_layout_orderby, UtilController.convertDpToPixel(15f, this@SearchActivity).toInt(), 0)

            }

        })

    }

    private fun onMenuItemClickListener() = OnMenuItemClickListener<PowerMenuItem>(){ position: Int, powerMenuItem: PowerMenuItem ->
        if(position==0){
            Log.v("test", queryTmp)
            this@SearchActivity.search_text_orderby?.text = "Upload date"
            var searchType = if(searchView?.searchType.equals("normal")) 1 else 0
            getSearchContentsList(if(queryTmp.startsWith("#")) queryTmp.substring(1) else queryTmp, searchType, 0)

        }else if(position==1){
            this@SearchActivity.search_text_orderby?.text = "View count"
            var searchType = if(searchView?.searchType.equals("normal")) 1 else 0
            getSearchContentsList(if(queryTmp.startsWith("#")) queryTmp.substring(1) else queryTmp, searchType, 2)
        }else if(position==2){
            this@SearchActivity.search_text_orderby?.text = "Rating"
            var searchType = if(searchView?.searchType.equals("normal")) 1 else 0
            getSearchContentsList(if(queryTmp.startsWith("#")) queryTmp.substring(1) else queryTmp, searchType, 1)
        }

        powerMenu?.dismiss()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        search_view.closeSearch()
        super.onDestroy()
    }

    fun getSearchContentsList(query : String, searchType : Int, sortType : Int){
        if(searchType==0){

            var sp : SharedPreferences = this@SearchActivity.getSharedPreferences("bridge", AppCompatActivity.MODE_PRIVATE)
            var messagesCall = api?.getSearchHashInfo(sp.getInt("userIdx", 0), query!!)
            messagesCall?.enqueue(object : Callback<Network> {
                override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                    var network = response!!.body()
                    Log.v("testtt", Gson().toJson(network))
                    if(network?.message.equals("ok")){
                        network.data?.get(0)?.hashcontents_list?.let {
                            if(it.size!=0){
                                search_layout_hash.visibility = View.VISIBLE
                                Glide.with(this@SearchActivity).load(it.get(0).hashImg).into(search_image_hash_thumbnail)
                                search_text_hash_name.text = it.get(0).hashName
                                search_text_hash_subcount.text = "Subscribers ${it.get(0).hashCnt}"
                                if(searchFlag){
                                    searchFlag=false
                                    var realm = Realm.getDefaultInstance()
                                    realm.executeTransaction {
                                        var searchWord = realm.createObject(SearchWord::class.java)
                                        searchWord.recentlyWord = if(query.startsWith("#")) "${query}" else "#${query}"
                                        searchWord.searchDateTime = Date().toString()
                                        searchWord.thumbnailImage = network.data?.get(0)?.hashcontents_list?.get(0)!!.hashImg
                                    }
                                    realm.close()
                                }

                                if(it.get(0).subflagresult==0){
                                    hashSubFlag = 0
                                    search_image_hash_sub.setImageResource(R.drawable.subscribe_normal_btn)
                                    search_text_hash_sub.setTextColor(Color.parseColor("#D1D1D1"))
                                }else{
                                    hashSubFlag = 1
                                    search_image_hash_sub.setImageResource(R.drawable.subscribe_active_btn)
                                    search_text_hash_sub.setTextColor(Color.parseColor("#E31C9E"))
                                }

                                search_layout_hash_sub.setOnClickListener {
                                    if(hashSubFlag==0){
                                        //bestchannel_image_subscribe.init(_context as Activity?)
                                        var sp : SharedPreferences = this@SearchActivity.getSharedPreferences("bridge", AppCompatActivity.MODE_PRIVATE)
                                        var messagesCall = api?.subscribeModify(Hash(network.data?.get(0)?.hashcontents_list?.get(0)?.hashName!!, sp.getInt("userIdx", 0)))
                                        messagesCall?.enqueue(object : Callback<Network> {
                                            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                                                var network = response!!.body()
                                                Log.v("subscribeModify : ", Gson().toJson(network))
                                                if(network?.message.equals("ok")){
                                                    hashSubFlag = 1
                                                    search_image_hash_sub.setImageResource(R.drawable.subscribe_active_btn)
                                                    search_text_hash_sub.setTextColor(Color.parseColor("#E31C9E"))
                                                    Toast.makeText(this@SearchActivity, "${network.data?.get(0)?.hashcontents_list?.get(0)?.hashName} added", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                            override fun onFailure(call: Call<Network>?, t: Throwable?) {

                                            }
                                        })
                                    }else{
                                        //bestchannel_image_subscribe.init(_context as Activity?)
                                        var hashName = network.data?.get(0)?.hashcontents_list?.get(0)?.hashName!!
                                        var sp : SharedPreferences = this@SearchActivity.getSharedPreferences("bridge", AppCompatActivity.MODE_PRIVATE)

                                        var messagesCall = api?.subscribeModify(Hash(hashName, sp.getInt("userIdx", 0)))
                                        messagesCall?.enqueue(object : Callback<Network> {
                                            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                                                var network = response!!.body()
                                                Log.v("subscribeModify : ", Gson().toJson(network))
                                                if(network?.message.equals("ok")){
                                                    hashSubFlag = 0
                                                    search_image_hash_sub.setImageResource(R.drawable.subscribe_normal_btn)
                                                    search_text_hash_sub.setTextColor(Color.parseColor("#D1D1D1"))
                                                    Toast.makeText(this@SearchActivity, "${hashName} removed", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                            override fun onFailure(call: Call<Network>?, t: Throwable?) {

                                            }
                                        })
                                    }
                                }
                            }else{
                                search_layout_hash.visibility = View.GONE
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<Network>?, t: Throwable?) {
                    Log.v("test", t.toString())

                }
            })
        }else{
            search_layout_hash.visibility = View.GONE
        }
//        var messagesCall = api?.searchContents(0, query!! ,searchType, sortType)
//        messagesCall?.enqueue(object : Callback<Network> {
//            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
//                var network = response!!.body()
//                Log.v("test", Gson().toJson(network))
//                if(network?.message.equals("ok")){
//                    network.data?.get(0)?.contents_list?.let {
//                        if(it.size!=0){
//                            searchResultAdapter?.clear()
//                            searchResultAdapter?.setEmpty(false)
//                            searchResultAdapter?.addAll(it)
//                            searchResultAdapter?.notifyDataSetChanged()
//                        }else{
//                            searchResultAdapter?.clear()
//                            var noresult = ArrayList<Content>()
//                            noresult.add(Content())
//                            searchResultAdapter?.setEmpty(true)
//                            searchResultAdapter?.addAll(noresult)
//                            searchResultAdapter?.notifyDataSetChanged()
//                        }
//                    }
//                }
//            }
//            override fun onFailure(call: Call<Network>?, t: Throwable?) {
//
//            }
//        })
    }
}
