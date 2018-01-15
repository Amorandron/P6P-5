package com.ykapps.bigmovie.models;

import java.math.BigDecimal;

public class Movie {

    private int movie_id;

    private String title;

    private int release_year;

    private int occurence;

    private String mpaa_rating;

    private String mpaa_reason;

    private int rating;

    private int rating_votes;

    private BigDecimal budget;

    public Movie(int movie_id, String title, int release_year, int occurence, String mpaa_rating, String mpaa_reason,
                 int rating, int rating_votes, BigDecimal budget) {

        this.movie_id = movie_id;
        this.title = title;
        this.release_year = release_year;
        this.occurence = occurence;
        this.mpaa_rating = mpaa_rating;
        this.mpaa_reason = mpaa_reason;
        this.rating = rating;
        this.rating_votes = rating_votes;
        this.budget = budget;
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

    public int getOccurence() {
        return occurence;
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
}