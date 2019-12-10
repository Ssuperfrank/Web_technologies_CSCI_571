package com.weather.frank;

public class WeatherCurrently {
    private long time;
    private String summary;
    private String icon;
    private double nearestStormDistance;
    private double nearestStormBearing;
    private double precipIntensity;
    private double precipProbability;
    private double temperature;
    private double apparentTemperature;
    private double dewPoint;
    private double humidity;
    private double pressure;
    private double windSpeed;
    private double windGust;
    private double windBearing;
    private double cloudCover;
    private double uvIndex;
    private double visibility;
    private double ozone;

    public long getTime() {
        return time;
    }

    public String getIcon() { return icon; }

    public String getSummary() {
        return summary;
    }

    public String getTemperature() {
        return String.valueOf(Math.round(temperature)) + "°F";
    }


    public String getHumidity() {
            return String.valueOf( Math.round(100 * humidity)) + " %";
    }

    public String getWindSpeed() {
        return String.format("%.2f", windSpeed) + " mph";
    }

    public String getVisibility() {
        return String.format("%.2f", visibility) + " km";
    }

    public String getPressure() {
        return String.format("%.2f", pressure) + " mb";
    }

    public String getCloudCover() {
        return String.valueOf( Math.round(100 * cloudCover)) + " %";
    }

    public String getOzone() {
        return String.format("%.2f", ozone) + " DU";
    }

    public String getPrecipitation() {
        return String.format("%.2f", precipIntensity) + " mmph";
    }
}
