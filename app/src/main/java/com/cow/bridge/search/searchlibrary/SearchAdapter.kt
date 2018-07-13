package com.cow.bridge.search.searchlibrary

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

import com.cow.bridge.R
import com.cow.bridge.model.SearchWord

import java.util.ArrayList

import io.realm.Realm
import io.realm.RealmResults

/**
 * Suggestions Adapter.
 *
 * @author Miguel Catalan Bañuls
 */
class SearchAdapter : BaseAdapter, Filterable {

    private var data: ArrayList<String>? = null
    private var suggestions: Array<String?>? = null
    private var suggestionIcon: Drawable? = null
    private var inflater: LayoutInflater? = null
    private var ellipsize: Boolean = false
    private var searchType = "normal"
    private var _context : Context? = null

    constructor(context: Context, suggestions: Array<String?>?) {
        inflater = LayoutInflater.from(context)
        data = ArrayList()
        this.suggestions = suggestions
    }

    constructor(context: Context, suggestions: Array<String?>?, suggestionIcon: Drawable?, ellipsize: Boolean) {
        inflater = LayoutInflater.from(context)
        _context = context
        data = ArrayList()
        this.suggestions = suggestions
        this.suggestionIcon = suggestionIcon
        this.ellipsize = ellipsize
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filterResults = FilterResults()
                //if (!TextUtils.isEmpty(constraint)) {
                // Retrieve the autocomplete results.
                val searchData = ArrayList<String>()
                for (string in suggestions!!) {
                    if (string?.matches(".*$constraint.*".toRegex())!!) {
                        if (searchType == "hash") {
                            if (string?.startsWith("#")!!) {
                                searchData.add(string!!)
                            }
                        } else {
                            if (!string?.startsWith("#")) {
                                searchData.add(string!!)
                            }
                        }
                    }

                }

                // Assign the data to the FilterResults
                filterResults.values = searchData
                filterResults.count = searchData.size
                //}
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                if (results.values != null) {
                    data = results.values as ArrayList<String>
                    notifyDataSetChanged()
                }
            }
        }
    }

    fun setSearchType(searchType: String) {
        this.searchType = searchType
    }


    override fun getCount(): Int {
        return data!!.size
    }

    override fun getItem(position: Int): Any {
        return if (data!![position].startsWith("#")) {
            data!![position]
        } else {
            data!![position]
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var viewHolderNormal: SuggestionsViewHolderNormal? = null
        var viewHolderHash: SuggestionsViewHolderHash? = null

        val currentListData = getItem(position) as String
        if (currentListData.startsWith("#")) {
            convertView = inflater!!.inflate(R.layout.row_search_hash, parent, false)
            viewHolderHash = SuggestionsViewHolderHash(convertView)
            convertView.tag = viewHolderHash
            viewHolderHash = convertView.tag as SuggestionsViewHolderHash
        } else {
            convertView = inflater!!.inflate(R.layout.row_search_normal, parent, false)
            viewHolderNormal = SuggestionsViewHolderNormal(convertView)
            convertView.tag = viewHolderNormal
            viewHolderNormal = convertView.tag as SuggestionsViewHolderNormal
        }

        if (currentListData.startsWith("#")) {
            var realm :Realm = Realm.getDefaultInstance()
            val currentWord = realm.where(SearchWord::class.java).equalTo("recentlyWord", currentListData).findFirst()
            if (currentWord != null) {
                viewHolderHash!!.wordText.text = currentWord.recentlyWord
                viewHolderHash.dateText.text = currentWord.searchDateTime
                viewHolderHash.deleteImage.setOnClickListener {
                    realm = Realm.getDefaultInstance()
                    realm.executeTransaction { realm ->
                        val searchWord = realm.where(SearchWord::class.java).equalTo("recentlyWord", currentListData).findFirst()
                        if (searchWord != null) {
                            searchWord.deleteFromRealm()
                            data!!.removeAt(position)
                        }
                        notifyDataSetChanged()
                    }
                    realm.close()
                }
            }
            Glide.with(_context).load(currentWord.thumbnailImage).into(viewHolderHash?.thumbnailImage);

            realm.close()
        } else {
            var realm = Realm.getDefaultInstance()
            val currentWord = realm.where(SearchWord::class.java).equalTo("recentlyWord", currentListData).findFirst()
            if (currentWord != null) {
                viewHolderNormal!!.wordText.text = currentWord.recentlyWord
                viewHolderNormal.dateText.text = currentWord.searchDateTime
                viewHolderNormal.deleteImage.setOnClickListener {
                    realm = Realm.getDefaultInstance()
                    realm.executeTransaction { realm ->
                        val searchWord = realm.where(SearchWord::class.java).equalTo("recentlyWord", currentListData).findFirst()
                        if (searchWord != null) {
                            searchWord.deleteFromRealm()
                            data!!.removeAt(position)
                        }
                        notifyDataSetChanged()
                    }
                    realm.close()
                }
            }
            realm.close()
        }

        return convertView
    }

    private inner class SuggestionsViewHolderNormal(convertView: View) {

        internal var wordText: TextView
        internal var dateText: TextView
        internal var deleteImage: ImageView

        init {
            wordText = convertView.findViewById<View>(R.id.search_text_word) as TextView
            dateText = convertView.findViewById<View>(R.id.search_text_datetime) as TextView
            deleteImage = convertView.findViewById<View>(R.id.search_image_delete) as ImageView
        }
    }

    private inner class SuggestionsViewHolderHash(convertView: View) {

        internal var wordText: TextView
        internal var dateText: TextView
        internal var deleteImage: ImageView
        internal var thumbnailImage: ImageView

        init {
            wordText = convertView.findViewById<View>(R.id.search_text_word) as TextView
            dateText = convertView.findViewById<View>(R.id.search_text_datetime) as TextView
            deleteImage = convertView.findViewById<View>(R.id.search_image_delete) as ImageView
            thumbnailImage = convertView.findViewById<View>(R.id.search_image_thumbnail) as ImageView
        }
    }
}