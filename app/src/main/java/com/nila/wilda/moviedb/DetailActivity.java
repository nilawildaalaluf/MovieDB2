package com.nila.wilda.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.nila.wilda.moviedb.config.api;
import com.nila.wilda.moviedb.helper.DatabaseHelper;
import com.nila.wilda.moviedb.util.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wilda on 7/8/17.
 */

public class DetailActivity extends AppCompatActivity {

    ImageView img_backdrop_path;
    TextView text_original_title, text_release_date, text_runtime, text_vote_average, text_overview;
    ListView list_trailer;

    ProgressBar _bar;
    LinearLayout linear;

    String link;
    boolean get_detail;

    SimpleAdapter simpleAdapter;
    ArrayList<HashMap<String, String>> trailers = new ArrayList<HashMap<String, String>>();

    public static String key;

    DatabaseHelper databaseHelper = new DatabaseHelper(DetailActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setTitle("DetailMovie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        api.id = getIntent().getStringExtra("id");

        link = "https://api.themoviedb.org/3/movie/" + api.id + "?api_key=7d55ff3af7ef009beea088a145ef8a21";
        get_detail = true; key = "";

        _bar                = (ProgressBar) findViewById(R.id._bar);
        linear              = (LinearLayout) findViewById(R.id.linear);

        img_backdrop_path   = (ImageView) findViewById(R.id.img_backdrop_path);
        text_original_title = (TextView) findViewById(R.id.text_original_title);
        text_release_date   = (TextView) findViewById(R.id.text_release_date);
        text_runtime        = (TextView) findViewById(R.id.text_runtime);
        text_vote_average   = (TextView) findViewById(R.id.text_vote_average);
        text_overview       = (TextView) findViewById(R.id.text_overview);
        list_trailer        = (ListView) findViewById(R.id.list_trailer);

        androidNetworking();
    }

    private void androidNetworking(){
        AndroidNetworking.get(link)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        _bar.setVisibility(View.GONE);
                        linear.setVisibility(View.VISIBLE);

                        if (get_detail){
                            Picasso.with(DetailActivity.this).
                                    load(api.backdrop_path + response.optString("backdrop_path"))
                                    .fit().centerCrop()
                                    .placeholder(R.drawable.image)
                                    .into(img_backdrop_path);

                            text_original_title.setText( response.optString("original_title") );
                            text_release_date.setText( response.optString("release_date") );
                            text_runtime.setText( response.optString("runtime") + " menit");
                            text_vote_average.setText("Vote: " +
                                    response.optString("vote_average") + " / " +"total " + response.optString("vote_count") );
                            text_overview.setText( response.optString("overview") );

                            get_detail = false;
                            link = "https://api.themoviedb.org/3/movie/" + api.id + "/videos?api_key=7d55ff3af7ef009beea088a145ef8a21&language=en-US";
                            androidNetworking();

                        } else {

                            try {
                                JSONArray jsonArray = response.optJSONArray("results");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject responses = jsonArray.getJSONObject(i);
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("key", responses.optString("key"));
                                    map.put("name", responses.optString("name"));

                                    trailers.add(map);
                                }

                                Adapter();
                            } catch (JSONException e){}

                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void Adapter(){

        simpleAdapter = new SimpleAdapter(this, trailers, R.layout.list_trailer,
                new String[] { "key", "name"},
                new int[] {R.id.text_key, R.id.text_name});

        list_trailer.setAdapter(simpleAdapter);
        Utility.setListViewHeightBasedOnChildren(list_trailer);
        list_trailer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                key = ((TextView) view.findViewById(R.id.text_key)).getText().toString();
                startActivity(new Intent(DetailActivity.this, YoutubeActivity.class));
//

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_rev) {
            startActivity(new Intent(DetailActivity.this, ReviewsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void bookmarkThis(View view) {
//        api.id = getIntent().getStringExtra("id");
//        data = getText().

        Toast.makeText(getApplicationContext(), "Added to Bookmark", Toast.LENGTH_SHORT).show();
    }
}

