package com.weather.frank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Toolbar myToolbar;

    private ViewPager viewPage;
    private TabLayout tabLayout;
    CoordinatorLayout progress_bar;

    private PagerViewAdapter pagerViewAdapter;
    SharedPreferences sharedPreferences;

    private SearchView mySearchView;
    private SearchView.SearchAutoComplete mySearchAutoComplete;
    private AutoSuggestAdapter autoSuggestAdapter;

    private String current_city;

    private Handler handler;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;

    private String weatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        sharedPreferences = getSharedPreferences("favorite", MODE_PRIVATE);
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_main);


        myToolbar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(myToolbar);

        tabLayout = findViewById(R.id.main_dots_tab);
        progress_bar = findViewById(R.id.progress_bar_layout);
        viewPage = findViewById(R.id.main_viewPage);

        tabLayout.setupWithViewPager(viewPage, true);
        viewPage.setVisibility(View.GONE);




        handler =new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d("handle", "handleMessage: " +msg);

                return false;
            }
        });


        APIRequest.getCurrentPlaceByIP(getApplicationContext(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject res) {
                try{
                    Log.d("test", "onResponse: ");

                    current_city =  res.getString("city");
                    String query = "lati=" + res.get("lat").toString() + "&long=" + res.get("lon").toString();
                    APIRequest.getCityWeatherInfo(getApplicationContext(), query, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            weatherInfo = response.toString();



                            pagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager(), current_city, weatherInfo, sharedPreferences.getAll());
                            viewPage.setAdapter(pagerViewAdapter);
                            viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int i, float v, int i1) {

                                }

                                @Override
                                public void onPageSelected(int i) {


                                }

                                @Override
                                public void onPageScrollStateChanged(int i) {

                                }

                            });

                            viewPage.setVisibility(View.VISIBLE);
                            progress_bar.setVisibility(View.GONE);
                        }
                    });
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.tooltab_search_bar);

        mySearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mySearchAutoComplete =  mySearchView.findViewById(R.id.search_src_text);


        autoSuggestAdapter = new AutoSuggestAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line);
        mySearchAutoComplete.setThreshold(1);
        mySearchAutoComplete.setAdapter(autoSuggestAdapter);
        mySearchAutoComplete.setBackgroundColor(getColor(R.color.cardBackgroud));
        mySearchAutoComplete.setDropDownBackgroundResource(R.color.whiteText);

        mySearchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    mySearchAutoComplete.setText(autoSuggestAdapter.getObject(position));
                }
        });

        mySearchAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,  AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(mySearchAutoComplete.getText())) {
                        requestAutoComplete(mySearchAutoComplete.getText().toString());
                    }
                }
                return false;
            }
        });

        mySearchAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_SEARCH){
                    //search
                    searchNewCity(v.getText().toString());
                }
                return true;
            }
        });


        mySearchView.onActionViewExpanded();

        return super.onCreateOptionsMenu(menu);
    }

    public void requestAutoComplete(String text){
        APIRequest.getAutoCities(getApplicationContext(), text, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<String> stringList = new ArrayList<>();
                Gson gson = new Gson();
                placePrediction places = gson.fromJson(response.toString(), placePrediction.class);
                if(response.toString().compareTo("{}")!= 0){
                    for (placePrediction.description item: places.getItem()){
                        stringList.add(item.getDescription());
                    }
                }
                //IMPORTANT: set data here and notify
                autoSuggestAdapter.setData(stringList);
                autoSuggestAdapter.notifyDataSetChanged();
            }
        });
    }

    /*
    *   search for a new city
    * */
    private void searchNewCity(String city) {
        Intent intent =new Intent(getApplicationContext(), SearchActivity.class);
        intent.putExtra("search_city_name", city);
//        startActivity(intent);
        startActivityForResult(intent, 1);
    }


    public void refresh(){
        pagerViewAdapter.initData(sharedPreferences.getAll());
        pagerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        data.getStringExtra("result");
        pagerViewAdapter.initData(sharedPreferences.getAll());
        pagerViewAdapter.notifyDataSetChanged();
    }

    public class placePrediction {

        private description [] predictions;

        public description[] getItem() {
            return predictions;
        }

        public class description {
            private String description;

            public String getDescription() {
                return description;
            }
        }
    }
}
