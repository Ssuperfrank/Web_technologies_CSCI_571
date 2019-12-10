package com.weather.frank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SummeryWeatherFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SummeryWeatherFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String city = "param1";
    private static final String weaParameter = "param2";
    private static final String current = "param3";

    // TODO: Rename and change types of parameters
    private String city_name;
    private String weatherInfo;
    private boolean ifCurrent;

    private String photoLinks;

    public SummeryWeatherFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param city_name Parameter 1.
     * @param weatherInfo Parameter 2.
     * @param ifCurrent place is current place if or not
     * @return A new instance of fragment SummeryWeatherFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static SummeryWeatherFrag newInstance(String city_name, String weatherInfo, boolean ifCurrent) {
        SummeryWeatherFrag fragment = new SummeryWeatherFrag();
        Bundle args = new Bundle();
        args.putString(city, city_name);
        args.putString(weaParameter, weatherInfo);
        args.putBoolean(current, ifCurrent);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city_name = getArguments().getString(city);
            weatherInfo = getArguments().getString(weaParameter);
            ifCurrent = getArguments().getBoolean(current);
        }

        APIRequest.getCityPhotos(getContext(),
                city_name,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                photoLinks = response.toString();
            }
        });

    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Gson gson = new Gson();
        final WeatherInfo weather = gson.fromJson(weatherInfo, WeatherInfo.class);

        View view = inflater.inflate(R.layout.fragment_summery_weather, container, false);

        /*
        * card 1 view
        *
        *
        * */
        ImageView icon = view.findViewById(R.id.weather_icon);
        TextView  temperature = view.findViewById(R.id.weather_temperature);
        TextView  summary = view.findViewById(R.id.weather_summary);
        TextView  city = view.findViewById(R.id.weather_city);

        icon.setImageResource(requestID.getWeatherIconId(weather.getCurrently().getIcon(), R.drawable.class));
        temperature.setText(weather.getCurrently().getTemperature());
        summary.setText(weather.getCurrently().getSummary());
        city.setText(city_name);


        /*
        * card 2 view
        * */

        ImageView icon_card_1 = view.findViewById(R.id.icon_current_card_1);
        ImageView icon_card_2 = view.findViewById(R.id.icon_current_card_2);
        ImageView icon_card_3 = view.findViewById(R.id.icon_current_card_3);
        ImageView icon_card_4 = view.findViewById(R.id.icon_current_card_4);

        TextView  num_card_1 = view.findViewById(R.id.num_current_card_1);
        TextView  num_card_2 = view.findViewById(R.id.num_current_card_2);
        TextView  num_card_3 = view.findViewById(R.id.num_current_card_3);
        TextView  num_card_4 = view.findViewById(R.id.num_current_card_4);

        TextView  name_card_1 = view.findViewById(R.id.name_current_card_1);
        TextView  name_card_2 = view.findViewById(R.id.name_current_card_2);
        TextView  name_card_3 = view.findViewById(R.id.name_current_card_3);
        TextView  name_card_4 = view.findViewById(R.id.name_current_card_4);


        num_card_1.setText(weather.getCurrently().getHumidity());
        num_card_2.setText(weather.getCurrently().getWindSpeed());
        num_card_3.setText(weather.getCurrently().getVisibility());
        num_card_4.setText(weather.getCurrently().getPressure());


        /*
        * card 3 view
        *
        * */
        int count = 1;
        for( WeatherDaily.dailyData day : weather.getDaily().getData()){
            TextView date =  view.findViewById(requestID.getWeeklyDateID(count, R.id.class));
            ImageView weekIcon = view.findViewById(requestID.getWeeklyIconId(count, R.id.class));
            TextView  low_temp = view.findViewById(requestID.getWeeklyLowTempId(count, R.id.class));
            TextView  high_temp = view.findViewById(requestID.getWeeklyHighTempId(count, R.id.class));

            date.setText(day.getTime());
            weekIcon.setImageResource(requestID.getWeatherIconId(day.getIcon(), R.drawable.class));
            low_temp.setText(day.getTemperatureLow());
            high_temp.setText(day.getTemperatureHigh());

            count++;
        }


        CardView card_1 = view.findViewById(R.id.summary_card_1);
        card_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailWeather.class);
                intent.putExtra("cityName", city_name);
                intent.putExtra("weather", weatherInfo);
                intent.putExtra("photo_tabs_link", photoLinks);
                startActivity(intent);
            }
        });

        final FloatingActionButton fab = view.findViewById(R.id.fab);
        if (ifCurrent){
            fab.setVisibility(View.GONE);
        }else {
            fab.setVisibility(View.VISIBLE);
            fab.setSelected(false);

        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences =   getActivity().getSharedPreferences("favorite", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =  sharedPreferences.edit();
                editor.remove(city_name);
                editor.commit();
                ((MainActivity)getActivity()).refresh();
                onDetach();
                onDestroy();
            }
        });



        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();

    }



}
