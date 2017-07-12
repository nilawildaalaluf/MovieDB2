package com.nila.wilda.moviedb;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.nila.wilda.moviedb.fragment.FavoriteFragment;
import com.nila.wilda.moviedb.fragment.HomeFragment;
import com.nila.wilda.moviedb.fragment.MostPapularFragment;
import com.nila.wilda.moviedb.fragment.TopRatedFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * Created by wilda on 7/8/17.
 */

public class MainActivity extends AppCompatActivity {

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_home) {
                    fragment = new HomeFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    getSupportActionBar().setTitle("Home");
                    ft.replace(R.id.container, fragment);
                    ft.commit();
                } else if (tabId == R.id.tab_popular) {
                    fragment = new MostPapularFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    getSupportActionBar().setTitle("Popular Movies");
                    ft.replace(R.id.container, fragment);
                    ft.commit();
                } else if (tabId == R.id.tab_toprated) {
                    fragment = new TopRatedFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    getSupportActionBar().setTitle("Top Rated");
                    ft.replace(R.id.container, fragment);
                    ft.commit();
                } else if (tabId == R.id.tab_favorite) {
                    fragment = new FavoriteFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    getSupportActionBar().setTitle("Bookmark");
                    ft.replace(R.id.container, fragment);
                    ft.commit();
                } else {
                    fragment = new HomeFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, fragment);
                    ft.commit();
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                //Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
            }
        });
    }
}
