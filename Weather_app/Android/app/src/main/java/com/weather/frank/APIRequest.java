package com.weather.frank;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;
import android.util.Log;
import android.view.textclassifier.TextLinks;


public class APIRequest {

    public static void getCurrentLocationWeatherInfo (final Context context, final Response.Listener<JSONObject> listener) {
        String ipUrl = "http://ip-api.com/json";

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, ipUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String url = "http://cs-usc-571.us-west-1.elasticbeanstalk.com/weather?lati="
                                    +response.get("lat").toString()
                                    +"&long="
                                    +response.get("lon").toString();
                            JsonObjectRequest weatherInfo = new JsonObjectRequest(Request.Method.GET,
                                    url, null,
                                    listener,
                                    new ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e("getCurrentLocationWeatherInfo", "onErrorResponse: ", error);
                                        }
                                    });
                            Singleton.getInstance(context).addToRequestQueue(weatherInfo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getCurrentLocationIP", "onErrorResponse: ", error);
            }
        });
        Singleton.getInstance(context).addToRequestQueue(jsonObjRequest);
    }



    public static void getCurrentPlaceByIP (Context context, Response.Listener<JSONObject> listener){
        String url = "http://ip-api.com/json";
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url,
                null,
                listener,
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("getCurrentPlaceByIP", "onErrorResponse: ", error);
                    }
                });
        Singleton.getInstance(context).addToRequestQueue(jsonObjRequest);
    }


    public static void getCityWeatherInfo (Context ctx, String query,
                                           Response.Listener<JSONObject> listener){

        String url = "http://cs-usc-571.us-west-1.elasticbeanstalk.com/weather?" + query;
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, listener, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getCityWeatherInfo", "onErrorResponse: ", error);
            }
        });
        Singleton.getInstance(ctx).addToRequestQueue(jsonObjRequest);

    }

    public static void getAutoCities (Context context, String query, Response.Listener<JSONObject> listener) {
        String url = "http://cs-usc-571.us-west-1.elasticbeanstalk.com/autoComplete?input=" + query;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                listener,
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("getAutoCities", "onErrorResponse: ", error);
                    }
                });
        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    public static  void getCityPhotos (Context context, String query, Response.Listener<JSONObject> listener){
        String url = "http://cs-usc-571.us-west-1.elasticbeanstalk.com/searchPhotos?city=" + query;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                listener,
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("getCityPhotos", "onErrorResponse: ", error);
                    }
                });
        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    public static  void getLocationByName(Context context, String query, Response.Listener<JSONObject> listener){
        String url =  "http://cs-usc-571.us-west-1.elasticbeanstalk.com/googleLocation?location=" + query;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                listener,
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("getLocationByName", "onErrorResponse: ", error);
                    }
                });
        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }



}
