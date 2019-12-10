package com.weather.frank;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.*;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeeklyTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeeklyTab extends Fragment {
    private static final String weekly_tab = "param1";

    private String weather_weekly;
    private WeatherDaily  weatherDaily;


    public WeeklyTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param weekly Parameter 1.
     * @return A new instance of fragment WeeklyTab.
     */
    // TODO: Rename and change types and number of parameters
    public static WeeklyTab newInstance(String weekly) {
        WeeklyTab fragment = new WeeklyTab();
        Bundle args = new Bundle();
        args.putString(weekly_tab, weekly);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Gson gson = new Gson();
            weather_weekly = getArguments().getString(weekly_tab);
            weatherDaily = gson.fromJson(weather_weekly, WeatherDaily.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_weekly_tab, container, false);

        ImageView icon = view.findViewById(R.id.weekly_tab_icon);
        TextView summary = view.findViewById(R.id.weekly_tab_summary);


        LineChart chart = view.findViewById(R.id.lineChart);



        icon.setImageResource(requestID.getWeatherIconId(weatherDaily.getIcon(), R.drawable.class));
        summary.setText(weatherDaily.getSummary());

        WeatherDaily.dailyData[] data = weatherDaily.getData();

        List<Entry> entriesLow = new ArrayList<Entry>();
        List<Entry> entriesHigh = new ArrayList<Entry>();


        for (int i = 0; i< data.length; i++){
            entriesLow.add(new Entry(i, (float) data[i].getTempLowData() ));
            entriesHigh.add(new Entry(i, (float) data[i].getTempHighData() ));
        }

        LineDataSet dataSetLow = new LineDataSet(entriesLow, "Minimum Temperature"); // add entries to dataset
        LineDataSet dataSetHigh = new LineDataSet(entriesHigh, "Maximum Temperature"); // add entries to dataset


        dataSetLow.setColor(Color.parseColor("#BB86FC"));
        dataSetHigh.setColor(Color.parseColor("#ffffbb33"));


        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(dataSetLow);
        dataSets.add(dataSetHigh);
        LineData  lineData = new LineData(dataSets);


        chart.getLegend().setTextColor(Color.WHITE);
        chart.getLegend().setTextSize(16);
        chart.getDescription().setText("");

        chart.getAxisRight().setEnabled(true);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setTextColor(Color.WHITE);

        chart.getAxisLeft().setEnabled(true);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getAxisLeft().setDrawGridLines(false);

        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setDrawLabels(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.TOP);
        chart.getXAxis().setTextColor(Color.WHITE);


        chart.setData(lineData);
        chart.invalidate(); // refresh







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
