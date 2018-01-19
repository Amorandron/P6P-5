package com.janwilts.bigmovie.chatbot.models;

import java.math.BigDecimal;

public class Movie implements Model {

    private Integer movie_id;

    private String title;

    private Integer release_year;

    private String type;

    private Integer occurence;

    private String mpaa_rating;

    private String mpaa_reason;

    private Integer rating;

    private Integer rating_votes;

    private BigDecimal budget;

    public Movie() {
    }

    public Movie(Integer movie_id, String title, Integer release_year, String type, Integer occurence, String mpaa_rating,
                 String mpaa_reason, Integer rating, Integer rating_votes, BigDecimal budget) {

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

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRelease_year() {
        return release_year;
    }

    public void setRelease_year(Integer release_year) {
        this.release_year = release_year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOccurence() {
        return occurence;
    }

    public void setOccurence(Integer occurence) {
        this.occurence = occurence;
    }

    public String getMpaa_rating() {
        return mpaa_rating;
    }

    public void setMpaa_rating(String mpaa_rating) {
        this.mpaa_rating = mpaa_rating;
    }

    public String getMpaa_reason() {
        return mpaa_reason;
    }

    public void setMpaa_reason(String mpaa_reason) {
        this.mpaa_reason = mpaa_reason;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRating_votes() {
        return rating_votes;
    }

    public void setRating_votes(Integer rating_votes) {
        this.rating_votes = rating_votes;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}