package com.cow.bridge.search.activity

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.cow.bridge.R
import com.cow.bridge.home.adapter.SearchResultAdapter
import com.cow.bridge.home.dialog.OrderbyDialog
import com.cow.bridge.model.SearchWord
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.search.searchlibrary.MaterialSearchView
import com.cow.bridge.util.UtilController
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchView = findViewById(R.id.search_view)

        searchView?.setVoiceSearch(false)
        searchView?.setSubmitOnClick(true)
        searchView?.setCursorDrawable(R.drawable.color_cursor_white)

        searchView?.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView?.hidePreview()
                searchView?.hideKeyboard(searchView)

                searchView?.setLayoutParams(FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, UtilController.convertDpToPixel(48f, applicationContext).toInt()));
                queryTmp = query!!
                if(searchView?.searchType.equals("normal")){
                    var realm = Realm.getDefaultInstance()
                    realm.executeTransaction {
                        var searchWord = realm.createObject(SearchWord::class.java)
                        searchWord.recentlyWord = query
                        searchWord.searchDateTime = Date().time.toString()
                    }
                    realm.close()
                    getSearchContentsList(query!!, 1, 0)
                }else{
                    var realm = Realm.getDefaultInstance()
                    realm.executeTransaction {
                        var searchWord = realm.createObject(SearchWord::class.java)
                        searchWord.recentlyWord = "#${query}"
                        searchWord.searchDateTime = Date().time.toString()
                        searchWord.thumbnailImage = ""
                        //TODO 썸네일 넣기
                    }
                    realm.close()
                    getSearchContentsList(query!!, 0, 0)
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

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
        Log.v("test", "${normalRecentlyWords.size}")
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

        search_layout_orderby.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val orderbyDialog : OrderbyDialog = OrderbyDialog(this@SearchActivity, search_text_orderby.text.toString())
                orderbyDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                orderbyDialog.show()
                orderbyDialog.setOnDismissListener(object : DialogInterface.OnDismissListener{
                    override fun onDismiss(dialog: DialogInterface?) {
                        with(dialog as OrderbyDialog){
                            dialog.orderby?.let {
                                this@SearchActivity.search_text_orderby?.text = it
                                var searchType = if(searchView?.searchType.equals("normal")) 1 else 0
                                var sortType = if(orderby?.equals("Upload date")!!) 0 else if(orderby?.equals("Rating")!!) 1 else 2
                                getSearchContentsList(queryTmp, searchType, sortType)
                            }
                        }
                    }

                })
            }

        })

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
            search_layout_hash.visibility = View.VISIBLE
        }else{
            search_layout_hash.visibility = View.GONE
        }
        var messagesCall = api?.searchContents(0, query!! ,searchType, sortType)
        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                if(network?.message.equals("ok")){
                    network.data?.get(0)?.contents_list?.let {
                        if(it.size!=0){
                            searchResultAdapter?.clear()
                            searchResultAdapter?.addAll(it)
                            searchResultAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Network>?, t: Throwable?) {

            }
        })
    }
}
