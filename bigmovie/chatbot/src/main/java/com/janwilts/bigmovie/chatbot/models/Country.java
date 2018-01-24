package com.janwilts.bigmovie.chatbot.models;

@Focusable
public class Country implements Model{

    private Integer country_id;

    private String country;

    public Country() {
    }

    public Country(Integer country_id, String country) {
        this.country_id = country_id;
        this.country = country;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
