package com.cow.bridge.search.searchlibrary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cow.bridge.R;
import com.cow.bridge.model.SearchWord;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Suggestions Adapter.
 *
 * @author Miguel Catalan Bañuls
 */
public class SearchAdapter extends BaseAdapter implements Filterable {

    private ArrayList<String> data;
    private String[] suggestions;
    private Drawable suggestionIcon;
    private LayoutInflater inflater;
    private boolean ellipsize;
    private Context _context;
    private String searchType = "normal";
    private RealmResults<SearchWord> searchWordResults;
    Realm realm;

    public SearchAdapter(Context context, String[] suggestions) {
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
        this.suggestions = suggestions;
    }

    public SearchAdapter(Context context, String[] suggestions, Drawable suggestionIcon, boolean ellipsize) {
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
        this.suggestions = suggestions;
        this.suggestionIcon = suggestionIcon;
        this.ellipsize = ellipsize;
        this.realm = Realm.getDefaultInstance();
        this.searchWordResults = realm.where(SearchWord.class).findAll();
        this.realm.close();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (!TextUtils.isEmpty(constraint)) {

                    // Retrieve the autocomplete results.
                    List<String> searchData = new ArrayList<>();

                    for (String string : suggestions) {
                        if (string.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            searchData.add(string);
                        }

                        if(string.matches(".*" + constraint+".*")){
                            if(searchType.equals("hash")){
                                if(string.startsWith("#")){
                                    searchData.add(string);
                                }
                            }else{
                                if(!string.startsWith("#")){
                                    searchData.add(string);
                                }
                            }
                        }

                    }

                    // Assign the data to the FilterResults
                    filterResults.values = searchData;
                    filterResults.count = searchData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null) {
                    data = (ArrayList<String>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
        return filter;
    }

    public void setSearchType(String searchType){
        this.searchType = searchType;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        if(data.get(position).startsWith("#")){
            return data.get(position).substring(1);
        }else{
            return data.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SuggestionsViewHolderNormal viewHolderNormal = null;
        SuggestionsViewHolderHash viewHolderHash = null;

        String currentListData = (String) getItem(position);

        if(currentListData.matches("#.*")) {
            convertView = inflater.inflate(R.layout.row_search_hash, parent, false);
            viewHolderHash = new SuggestionsViewHolderHash(convertView);
            convertView.setTag(viewHolderHash);
            viewHolderHash = (SuggestionsViewHolderHash) convertView.getTag();
        }else {
            convertView = inflater.inflate(R.layout.row_search_normal, parent, false);
            viewHolderNormal = new SuggestionsViewHolderNormal(convertView);
            convertView.setTag(viewHolderNormal);
            viewHolderNormal = (SuggestionsViewHolderNormal) convertView.getTag();
        }

        if(currentListData.matches("#.*")) {
            //Campus currentCampus = campusRealmResults.get(Integer.parseInt(currentListData.replace("c", "")));
            this.realm = Realm.getDefaultInstance();
            SearchWord currentWord = realm.where(SearchWord.class).equalTo("word", currentListData).findFirst();

            //Glide.with(_context).load().into(viewHolderHash.thumbnailImage);
            viewHolderHash.wordText.setText(currentWord.getRecentlyWord());
            viewHolderHash.dateText.setText(currentWord.getSearchDateTime());
            viewHolderHash.deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            /*Campus currentCampus = realm.where(Campus.class).equalTo("campusCode", currentListData).findFirst();
            Glide.with(_context).load(ApplicationController.pictureEndpoint+"/mobile/logo/logo_"+currentCampus.getCampusCode()+".jpg").into(viewHolderCampus.logoImage);
            viewHolderCampus.campusText.setText(currentCampus.getCampusName());
            viewHolderCampus.countText.setText(currentCampus.getCampusReviewCount()+"개의 리뷰");
            if (ellipsize) {
                viewHolderCampus.campusText.setSingleLine();
                viewHolderCampus.campusText.setEllipsize(TextUtils.TruncateAt.END);
            }*/
            this.realm.close();
        }else {
            //Major currentMajor = majorRealmResults.get(Integer.parseInt(currentListData.replace("m", "")));
            this.realm = Realm.getDefaultInstance();
            SearchWord currentWord = realm.where(SearchWord.class).equalTo("word", currentListData).findFirst();

            viewHolderNormal.wordText.setText(currentWord.getRecentlyWord());
            viewHolderNormal.dateText.setText(currentWord.getSearchDateTime());
            viewHolderNormal.deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            this.realm.close();
        }

        return convertView;
    }

    private class SuggestionsViewHolderNormal {

        TextView wordText;
        TextView dateText;
        ImageView deleteImage;

        public SuggestionsViewHolderNormal(View convertView) {
            wordText = (TextView) convertView.findViewById(R.id.search_text_word);
            dateText = (TextView) convertView.findViewById(R.id.search_text_datetime);
            deleteImage = (ImageView) convertView.findViewById(R.id.search_image_delete);
        }
    }

    private class SuggestionsViewHolderHash {

        TextView wordText;
        TextView dateText;
        ImageView deleteImage;
        ImageView thumbnailImage;

        public SuggestionsViewHolderHash(View convertView) {
            wordText = (TextView) convertView.findViewById(R.id.search_text_word);
            dateText = (TextView) convertView.findViewById(R.id.search_text_datetime);
            deleteImage = (ImageView) convertView.findViewById(R.id.search_image_delete);
            thumbnailImage = (ImageView) convertView.findViewById(R.id.search_image_thumbnail);
        }
    }
}