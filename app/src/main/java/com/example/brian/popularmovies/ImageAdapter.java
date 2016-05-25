package com.example.brian.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Objects;

/**
 * Created by brian on 5/23/16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;

    public ImageAdapter(Context context){
        this.context = context;
    }

    public int getCount(){
        return 1;
    }

    public long getItemId(int position){
        return 0;
    }

    public Object getItem(int position){
        return null;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbIds);
        return imageView;
    }

   // private Integer[] mThumbIds = {

   // }


}
