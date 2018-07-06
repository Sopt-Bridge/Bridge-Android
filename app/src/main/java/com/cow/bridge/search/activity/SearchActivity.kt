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
import com.cow.bridge.search.searchlibrary.MaterialSearchView
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    var searchView : MaterialSearchView? = null

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

                searchView?.setLayoutParams(FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

                //TODO : 검색 결과 realm에 저장하기

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
        for(word in normalRecentlyWords){
            searchName[count] = word.recentlyWord!!
        }

        realm.close()

        searchView?.setSuggestions(searchName);

        val searchResultAdapter = SearchResultAdapter(this)

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
                                Log.v("test", orderby)
                                this@SearchActivity.search_text_orderby?.text = it
                                //TODO : 해당 정렬순으로 리스트가져오기
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
}
