package com.ykapps.p6p.models;

import java.math.BigDecimal;

public class Movie {

    private int movie_id;

    private String title;

    private int release_year;

    private int occurance;

    private String mpaa_rating;

    private String mpaa_reason;

    private int rating;

    private int rating_votes;

    private BigDecimal budget;

    private BigDecimal production_costs;

    public Movie(int movie_id, String title, int release_year, int occurance, String mpaa_rating, String mpaa_reason,
                 int rating, int rating_votes, BigDecimal budget, BigDecimal production_costs) {

        this.movie_id = movie_id;
        this.title = title;
        this.release_year = release_year;
        this.occurance = occurance;
        this.mpaa_rating = mpaa_rating;
        this.mpaa_reason = mpaa_reason;
        this.rating = rating;
        this.rating_votes = rating_votes;
        this.budget = budget;
        this.production_costs = production_costs;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public int getRelease_year() {
        return release_year;
    }

    public int getOccurance() {
        return occurance;
    }

    public String getMpaa_rating() {
        return mpaa_rating;
    }

    public String getMpaa_reason() {
        return mpaa_reason;
    }

    public int getRating() {
        return rating;
    }

    public int getRating_votes() {
        return rating_votes;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public BigDecimal getProduction_costs() {
        return production_costs;
    }
}