package com.ykapps.bigmovie.models;

public class Country {

    private Integer country_id;

    private String country;

    public Country(Integer country_id, String country) {
        this.country_id = country_id;
        this.country = country;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public String getCountry() {
        return country;
    }
}
