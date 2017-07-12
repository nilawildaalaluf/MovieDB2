package com.nila.wilda.moviedb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.nila.wilda.moviedb.config.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wilda on 7/8/17.
 */

public class ReviewsActivity extends AppCompatActivity {

    ListView list_rev;

    SimpleAdapter simpleAdapter;
    ArrayList<HashMap<String, String>> reviews = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        list_rev = (ListView) findViewById(R.id.list_rev);
        androidNetworking();

        getSupportActionBar().setTitle("Reviews");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void androidNetworking(){
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/" + api.id + "/reviews?api_key=7d55ff3af7ef009beea088a145ef8a21&language=en-US")
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
                                map.put("author", responses.optString("author"));
                                map.put("content", responses.optString("content"));

                                reviews.add(map);
                            }

                            Adapter();
                        } catch (JSONException e){

                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void Adapter(){

        simpleAdapter = new SimpleAdapter(this, reviews, R.layout.list_reviews,
                new String[] { "author", "content"},
                new int[] {R.id.text_author, R.id.text_content});

        list_rev.setAdapter(simpleAdapter);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
