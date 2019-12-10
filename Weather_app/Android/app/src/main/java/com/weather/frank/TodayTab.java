package com.weather.frank;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayTab extends Fragment {
    private static final String today_tab = "param1";

    private String weather_currently;
    private WeatherCurrently  weatherCurrently;

    public TodayTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param currently Parameter 1.
     * @return A new instance of fragment TodayTab.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayTab newInstance(String currently) {
        TodayTab fragment = new TodayTab();
        Bundle args = new Bundle();
        args.putString(today_tab, currently);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Gson gson = new Gson();
            weather_currently = getArguments().getString(today_tab);
            weatherCurrently = gson.fromJson(weather_currently, WeatherCurrently.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_tab, container, false);


        TextView windSpeed = view.findViewById(R.id.today_tab_windSpeed);
        TextView pressure= view.findViewById(R.id.today_tab_pressure);
        TextView precipitation= view.findViewById(R.id.today_tab_precipitation);
        TextView temperature= view.findViewById(R.id.today_tab_temperature);
        TextView humidity= view.findViewById(R.id.today_tab_humidity);
        TextView visibility= view.findViewById(R.id.today_tab_visibility);
        TextView cloudCover= view.findViewById(R.id.today_tab_cloud_cover);
        TextView ozone= view.findViewById(R.id.today_tab_ozone);
        TextView icon= view.findViewById(R.id.today_tab_icon_text);

        ImageView iconImage= view.findViewById(R.id.today_tab_icon);

        windSpeed.setText(weatherCurrently.getWindSpeed());
        pressure.setText(weatherCurrently.getPressure());
        precipitation.setText(weatherCurrently.getPrecipitation());
        temperature.setText(weatherCurrently.getTemperature());
        humidity.setText(weatherCurrently.getHumidity());
        visibility.setText(weatherCurrently.getVisibility());
        cloudCover.setText(weatherCurrently.getCloudCover());
        ozone.setText(weatherCurrently.getOzone());
        icon.setText(requestID.getTodayTabIconTextID(weatherCurrently.getIcon(),R.string.class));
        iconImage.setImageResource(requestID.getWeatherIconId(weatherCurrently.getIcon(),R.drawable.class));

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
