package com.weather.frank;

import android.util.Log;

import java.lang.reflect.Field;

public class requestID {


    public static int getWeatherIconId(String name,  Class<?> c){
        String [] fileName = name.split("-");
        StringBuilder sb = new StringBuilder();
        sb.append("weather");
        for (String sub: fileName) {
            sb.append("_");
            sb.append(sub);
        }
        try {
            Field idField = c.getDeclaredField(sb.toString());
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static int getWeeklyDateID(int num,  Class<?> c){
        String dateId = "date_" + String.valueOf(num);
        try {
            Field idField = c.getDeclaredField(dateId);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static int getWeeklyIconId(int num,  Class<?> c){
        String iconId = "weekly_icon_" + String.valueOf(num);
        try {
            Field idField = c.getDeclaredField(iconId);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static int getWeeklyLowTempId(int num,  Class<?> c){
        String tempLowId = "low_temp_" + String.valueOf(num);
        try {
            Field idField = c.getDeclaredField(tempLowId);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static int getWeeklyHighTempId(int num,  Class<?> c){
        String tempHighId = "high_temp_" + String.valueOf(num);
        try {
            Field idField = c.getDeclaredField(tempHighId);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static int getTodayTabIconTextID(String icon,  Class<?> c){
        String [] iconName = icon.split("-");
        StringBuilder sb = new StringBuilder();
        sb.append("today_icon");
        for (String sub: iconName) {
            sb.append("_");
            sb.append(sub);
        }
        try {
            Field idField = c.getDeclaredField(sb.toString());
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


}
