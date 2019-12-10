package com.weather.frank;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;


public class DetailWeather extends AppCompatActivity {
    private Toolbar myToolbar;
    private Intent intent;
    private Gson gson = new Gson();
    private String city_name;
    private WeatherInfo weather;
    private WeatherCurrently weatherCurrently;
    private WeatherDaily weatherDaily;
    private String photosLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        weather = gson.fromJson(intent.getStringExtra("weather"), WeatherInfo.class);
        city_name = intent.getStringExtra("cityName");
        photosLinks = intent.getStringExtra("photo_tabs_link");
        weatherCurrently = weather.getCurrently();
        weatherDaily = weather.getDaily();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);

        myToolbar =  findViewById(R.id.detail_activity_toolbar);
        myToolbar.setTitle(city_name);
        setSupportActionBar(myToolbar);

        ViewPager viewPager = findViewById(R.id.detail_view_pager);
        TabLayout tabs = findViewById(R.id.tabs);

        SectionPagerAdapter sectionsPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) );

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
              tab.getIcon().setTint(Color.WHITE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setTint(Color.GRAY);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.twitter:
                tweet(city_name, weatherCurrently.getTemperature());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    public class SectionPagerAdapter extends FragmentPagerAdapter {

        final int TAB_NUM = 3;

        TodayTab todayTab;
        WeeklyTab weeklyTab;
        PhotosTab photosTab;

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
            todayTab = TodayTab.newInstance(gson.toJson(weatherCurrently));
            weeklyTab = WeeklyTab.newInstance(gson.toJson(weatherDaily));
            photosTab = PhotosTab.newInstance(photosLinks);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return todayTab;
                case 1: return weeklyTab;
                case 2: return photosTab;
            }
            return todayTab;
        }

        @Override
        public int getCount() {
            return TAB_NUM;
        }
    }



    public void tweet(String city, String temp) {
        String url = "https://twitter.com/intent/tweet?text=";
        url += "Check Out "+ city +"'s Weather! It is " + temp +"!";
        url += "&hashtags=CSCI571WeatherSearch";
        Uri content_url = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, content_url);
        startActivity(intent);
    }

}