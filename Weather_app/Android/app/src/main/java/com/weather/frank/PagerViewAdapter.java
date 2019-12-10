package com.weather.frank;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Map;

public class PagerViewAdapter extends FragmentPagerAdapter {

    ArrayList<SummeryWeatherFrag> list;
    ArrayList<Integer> listId;
    int id ;
    private String current_city;
    private String weatherInfo;
    SummeryWeatherFrag current;


    public PagerViewAdapter(FragmentManager fm, String current_place, String weather,  Map<String, ?> favoriteMap) {
        super(fm);
        id = 0;
        list = new ArrayList<>();
        listId = new ArrayList<>();
        current_city = current_place;
        weatherInfo = weather;
        initData(favoriteMap);

    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return  list.size();
    }

    @Override
    public long getItemId(int position) {

        return listId.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (object instanceof Fragment) {
            if (list.contains(object)) {
                return list.indexOf(object);
            } else {
                return POSITION_NONE;
            }
        }
        return super.getItemPosition(object);
    }

    public void initData(Map<String, ?> favoriteMap){
        list.clear();
        listId.clear();

        current = SummeryWeatherFrag.newInstance( current_city, weatherInfo, true);
        list.add(0,current);
        listId.add(0,id++);
        for (Map.Entry entry: favoriteMap.entrySet()){
            list.add(SummeryWeatherFrag.newInstance(entry.getKey().toString(), entry.getValue().toString(), false));
            listId.add(id++);
        }
    }

}
