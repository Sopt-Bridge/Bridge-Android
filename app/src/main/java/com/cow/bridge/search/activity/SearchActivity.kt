package com.cow.bridge.search.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.cow.bridge.R
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search_view.setVoiceSearch(false)
        search_view.setSubmitOnClick(true)
        search_view.setCursorDrawable(R.drawable.color_cursor_white)

        search_view.showSearch()


        search_view.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //TODO : 검색 결과 뿌려주기
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //TODO : 최근 검색 결과 보여주기
                return false
            }

        })

        search_view.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener{
            override fun onSearchViewClosed() {


            }

            override fun onSearchViewShown() {


            }

        })

    }

    override fun onResume() {
        super.onResume()
        search_view.showSearch()
    }

    override fun onDestroy() {
        search_view.closeSearch()
        super.onDestroy()
    }
}
