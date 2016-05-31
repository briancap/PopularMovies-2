package com.example.brian.popularmovies;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * Created by brian on 5/23/16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    public String[] images;

    DisplayMetrics metrics;
    int imageWidth;
    int imageHeight;

    public ImageAdapter(Context context, String[] images){
        this.context = context;
        this.images = images;

        metrics = context.getResources().getDisplayMetrics();
        imageWidth = (metrics.widthPixels)/2;
        imageHeight = (metrics.heightPixels/2);
    }

    public int getCount(){
        if(images !=null){
            return images.length;
        } else {
            return 0;
        }

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
        } else {
            imageView = (ImageView) convertView;
        }

        if(images != null) {
            Picasso.with(context)
                    .load(images[position])
                    .resize(imageWidth, imageHeight)
                    .into(imageView);
        }

        return imageView;
    }

    public void updateAdpater(String[] imagePaths){
        images = imagePaths;
        notifyDataSetChanged();
    }


}
