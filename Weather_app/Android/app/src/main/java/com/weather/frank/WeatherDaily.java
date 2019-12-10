package com.weather.frank;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherDaily {
    private String summary;
    private String icon;
    private dailyData[] data;

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public dailyData[] getData() {
        return data;
    }

    public class dailyData {

        private long time;
        private String summary;
        private String icon;
        private double  temperatureHigh;
        private double  temperatureLow;

        public String getTime() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
            return simpleDateFormat.format(new Date(time *1000));
        }

        public String getSummary() {
            return summary;
        }

        public String getIcon() {
            return icon;
        }
        public String getTemperatureHigh() {
            return String.valueOf(Math.round(temperatureHigh));
        }
        public String getTemperatureLow() {
            return String.valueOf(Math.round(temperatureLow));
        }


        public double getTempHighData() {
            return  temperatureHigh;
        }
        public double getTempLowData() {
            return temperatureLow;
        }





    }
}
