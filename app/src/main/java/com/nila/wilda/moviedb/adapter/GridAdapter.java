package com.nila.wilda.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.nila.wilda.moviedb.R;
import com.nila.wilda.moviedb.config.api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wilda on 7/8/17.
 */

public class GridAdapter extends SimpleAdapter {
    LayoutInflater inflater;
    Context context;
    int layout;
    ArrayList<HashMap<String, String>> arrayList;

    public GridAdapter(Context context, ArrayList<HashMap<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.arrayList = data;
        this.layout = resource;
        inflater.from(context);
    }


    public String getIdData(int position){
        return arrayList.get(position).get("id");
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
         //View view = super.getView(position, convertView, parent);
        View view = LayoutInflater.from (parent.getContext()).inflate(layout,null);

        ImageView image = (ImageView) view.findViewById(R.id.img_backdrop);

        Picasso.with(context).
                load( api.backdrop_path + arrayList.get(position).get("backdrop_path"))
                .fit().centerCrop()
                .placeholder(R.drawable.image)
                .into(image);

        return view;
    }
}
