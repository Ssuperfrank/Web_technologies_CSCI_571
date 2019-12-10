package com.weather.frank;

public class photoTabLinks {

    private String kind;
    private photoLinks[] items;


    public photoLinks[] getItems() {
        return items;
    }

    public class photoLinks{
        private String title;
        private String link;

        public String getLink() {
            return link;
        }
    }

}
