package com.weather.frank;

public class WeatherInfo {
    private double latitude;
    private double longitude;
    private String timezone;
    private WeatherCurrently currently;
    private WeatherDaily daily;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public WeatherCurrently getCurrently() {
        return currently;
    }

    public WeatherDaily getDaily() {
        return daily;
    }

}
