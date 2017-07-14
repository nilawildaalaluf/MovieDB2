package com.nila.wilda.moviedb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.nila.wilda.moviedb.DetailActivity;
import com.nila.wilda.moviedb.R;
import com.nila.wilda.moviedb.adapter.GridAdapter;
import com.nila.wilda.moviedb.config.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wilda on 7/8/17.
 */

public class HomeFragment extends Fragment {

    View view;
    GridView gridView;

    GridAdapter gridAdapter;
    ArrayList<HashMap<String, String>> movies = new ArrayList<HashMap<String, String>>();

    String link;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        //getSupportActionBar().setTitle("Pop Movies");

        link = "https://api.themoviedb.org/3/movie/popular?api_key=7d55ff3af7ef009beea088a145ef8a21&language=en-US&page=1";

        gridView = (GridView) view.findViewById(R.id.grid_movie);
        androidNetworking();

        return view;
    }

    private void androidNetworking() {

        movies.clear();
        gridView.setAdapter(null);

        AndroidNetworking.get(link)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.optJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject responses = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("id", responses.optString("id"));
                                map.put("backdrop_path", responses.optString("backdrop_path"));
                                movies.add(map);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        getAdapter();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void getAdapter() {
        if (getActivity() != null) {
            // Code goes here.
             gridAdapter = new GridAdapter(getActivity(), movies, R.layout.grid_movie,
                new String[]{"id", "backdrop_path"}, new int[]{R.id.text_id, R.id.img_backdrop});

            gridView.setAdapter(gridAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    api.id = ((TextView) view.findViewById(R.id.text_id)).getText().toString();
                    startActivity(new Intent(getActivity(), DetailActivity.class));
                    Log.d("Log id", api.id);
                }
            });

        }else {
            Toast.makeText(getActivity(),"Null Activity", Toast.LENGTH_SHORT).show();
        }


    }
}
