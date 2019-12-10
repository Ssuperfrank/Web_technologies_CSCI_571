package com.weather.frank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONObject;


public class SearchActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private Intent intent;
    private WeatherInfo weather;
    private String weatherInfo;
    private String city_name;
    private String photoLinks;

    private SharedPreferences sharedPreferences;


    private CoordinatorLayout progress_bar;
    private CoordinatorLayout search_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = getSharedPreferences("favorite",MODE_PRIVATE);
        intent = getIntent();
        city_name = intent.getStringExtra("search_city_name");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle(city_name);
        setSupportActionBar(myToolbar);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent res = new Intent();
                if ( sharedPreferences.contains(city_name) )

                res.putExtra("result", "changed");
                SearchActivity.this.setResult(RESULT_OK, intent);

                finish();
            }
        });

        progress_bar = findViewById(R.id.progress_bar_layout);
        search_content = findViewById(R.id.search_content_layout);


        AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
        asyncTaskRunner.execute();


        CardView card_1 = findViewById(R.id.summary_card_1);
        card_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailWeather.class);
                intent.putExtra("cityName", city_name);
                intent.putExtra("weather", weatherInfo);
                intent.putExtra("photo_tabs_link", photoLinks);
                startActivity(intent);
            }
        });

        final FloatingActionButton fab = findViewById(R.id.fab);

        if ( sharedPreferences.contains(city_name) ){
            fab.setSelected(false);
        } else {
            fab.setSelected(true);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if ( sharedPreferences.contains(city_name) ){
                    Toast.makeText(getApplicationContext(),city_name + "was removed from favorites",
                            Toast.LENGTH_SHORT).show();
                    fab.setSelected(true);
                    editor.remove(city_name);
                }else {
                    Toast.makeText(getApplicationContext(),city_name + "was added to favorites",
                            Toast.LENGTH_SHORT).show();
                    fab.setSelected(false);
                    editor.putString(city_name, weatherInfo);
                }
                editor.apply();

            }
        });
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requestPhotoLinks(city_name);
            requestNewPlaceWeather(city_name);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            weather = gson.fromJson(weatherInfo, WeatherInfo.class);

            setCard1();
            setCard2();
            setCard3();
            progress_bar.setVisibility(View.GONE);
            search_content.setVisibility(View.VISIBLE);

        }
    }


    public void requestNewPlaceWeather(String city_name) {
        APIRequest.getLocationByName(getApplicationContext(), city_name, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                GooglePlace googlePlace = gson.fromJson(response.toString(), GooglePlace.class);
                if (googlePlace.hasResults()){
                    GooglePlace.Location location = googlePlace.getFirstResult().getGeometry().getLocation();
                    String query = "lati=" + location.getLat() + "&long=" + location.getLng();
                    APIRequest.getCityWeatherInfo(getApplicationContext(), query, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                             weatherInfo =  response.toString();
                        }
                    });
                }
            }
        });
    }

    public void requestPhotoLinks(String city_name){
        APIRequest.getCityPhotos(getApplicationContext(), city_name, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                photoLinks = response.toString();
            }
        });
    }


    private void setCard1(){
        /*
         * card 1 view
         *
         * */
        ImageView icon = findViewById(R.id.weather_icon);
        TextView temperature = findViewById(R.id.weather_temperature);
        TextView  summary = findViewById(R.id.weather_summary);
        TextView  city = findViewById(R.id.weather_city);

        icon.setImageResource(requestID.getWeatherIconId(weather.getCurrently().getIcon(), R.drawable.class));
        temperature.setText(weather.getCurrently().getTemperature());
        summary.setText(weather.getCurrently().getSummary());
        city.setText(city_name);
    }

    private void setCard2(){
        /*
         * card 2 view
         * */
        ImageView icon_card_1 = findViewById(R.id.icon_current_card_1);
        ImageView icon_card_2 = findViewById(R.id.icon_current_card_2);
        ImageView icon_card_3 = findViewById(R.id.icon_current_card_3);
        ImageView icon_card_4 = findViewById(R.id.icon_current_card_4);

        TextView  num_card_1 = findViewById(R.id.num_current_card_1);
        TextView  num_card_2 = findViewById(R.id.num_current_card_2);
        TextView  num_card_3 = findViewById(R.id.num_current_card_3);
        TextView  num_card_4 = findViewById(R.id.num_current_card_4);

        TextView  name_card_1 = findViewById(R.id.name_current_card_1);
        TextView  name_card_2 = findViewById(R.id.name_current_card_2);
        TextView  name_card_3 = findViewById(R.id.name_current_card_3);
        TextView  name_card_4 = findViewById(R.id.name_current_card_4);


        num_card_1.setText(weather.getCurrently().getHumidity());
        num_card_2.setText(weather.getCurrently().getWindSpeed());
        num_card_3.setText(weather.getCurrently().getVisibility());
        num_card_4.setText(weather.getCurrently().getPressure());
    }

    private void setCard3() {
        /*
         * card 3 view
         *
         * */
        int count = 1;
        for( WeatherDaily.dailyData day : weather.getDaily().getData()){
            TextView date =  findViewById(requestID.getWeeklyDateID(count, R.id.class));
            ImageView weekIcon = findViewById(requestID.getWeeklyIconId(count, R.id.class));
            TextView  low_temp = findViewById(requestID.getWeeklyLowTempId(count, R.id.class));
            TextView  high_temp = findViewById(requestID.getWeeklyHighTempId(count, R.id.class));

            date.setText(day.getTime());
            weekIcon.setImageResource(requestID.getWeatherIconId(day.getIcon(), R.drawable.class));
            low_temp.setText(day.getTemperatureLow());
            high_temp.setText(day.getTemperatureHigh());

            count++;
        }
    }

}
