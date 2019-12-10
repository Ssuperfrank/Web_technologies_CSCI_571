package com.weather.frank;

public class GooglePlace {
    private Result[] results;

    public Result[] getResults() {
        return results;
    }

    public boolean hasResults(){
        if (results.length > 0){
            return true;
        }
        return false;
    }

    public Result getFirstResult(){
        return results[0];
    }

    public class Result{

        private Geometry geometry;

        public Geometry getGeometry() {
            return geometry;
        }
    }


    public class Geometry{
        private Location location;

        public Location getLocation() {
            return location;
        }
    }


    public class Location{
        private String lat;
        private String lng;

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }
    }


}
