package com.ykapps.bigmovie.models;

import java.math.BigDecimal;

public class Movie {

    private Integer movie_id;

    private String title;

    private Integer release_year;

    private String type;

    private Integer occurence;

    private String mpaa_rating;

    private String mpaa_reason;

    private Double rating;

    private Integer rating_votes;

    private BigDecimal budget;

    public Movie(Integer movie_id, String title, Integer release_year, String type, Integer occurence, String mpaa_rating,
                 String mpaa_reason, Double rating, Integer rating_votes, BigDecimal budget) {

        this.movie_id = movie_id;
        this.title = title;
        this.release_year = release_year;
        this.type = type;
        this.occurence = occurence;
        this.mpaa_rating = mpaa_rating;
        this.mpaa_reason = mpaa_reason;
        this.rating = rating;
        this.rating_votes = rating_votes;
        this.budget = budget;
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getRelease_year() {
        return release_year;
    }

    public String getType() {
        return type;
    }

    public Integer getOccurence() {
        return occurence;
    }

    public String getMpaa_rating() {
        return mpaa_rating;
    }

    public String getMpaa_reason() {
        return mpaa_reason;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getRating_votes() {
        return rating_votes;
    }

    public BigDecimal getBudget() {
        return budget;
    }
}