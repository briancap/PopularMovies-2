package com.example.brian.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by Brian on 9/30/2016.
 */
public class ReviewAdapter extends BaseAdapter{
    Map<Integer, Map<String, Object>> reviewData;


    public ReviewAdapter(Map<Integer, Map<String, Object>> reviewData){
        this.reviewData = reviewData;
    }

    @Override
    public int getCount() {
        if(reviewData != null){
            return reviewData.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView;

        if(convertView == null){
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reviews, parent, false);
        } else {
            rootView = convertView;
        }

        TextView author = (TextView) rootView.findViewById(R.id.review_author);
        TextView review = (TextView) rootView.findViewById(R.id.review_detail);

        author.setText(reviewData.get(position).get(Utility.COMMENT_AUTHOR_TAG).toString());
        review.setText(reviewData.get(position).get(Utility.COMMENTS_TAG).toString());


        return rootView;
    }
}
