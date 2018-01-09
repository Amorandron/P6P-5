package com.ykapps.bigmovie.models;

public class Country {

    private int country_id;

    private String country;

    public Country(int country_id, String country) {
        this.country_id = country_id;
        this.country = country;
    }

    public int getCountry_id() {
        return country_id;
    }

    public String getCountry() {
        return country;
    }
}
