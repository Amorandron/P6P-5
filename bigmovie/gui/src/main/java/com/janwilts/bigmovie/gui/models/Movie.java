package com.janwilts.bigmovie.gui.models;

import java.math.BigDecimal;

public class Movie {
    
    private int movie_id;
    private String title;
    private int release_year;
    private String type;
    private int occurence;
    private String mpaa_rating;
    private String mpaa_reason;
    private double rating;
    private int rating_votes;
    private BigDecimal budget;
    
    public Movie() {
    }
    
    public Movie(int movie_id, String title, int release_year, String type, int occurence, String mpaa_rating, String mpaa_reason, double rating, int rating_votes, BigDecimal budget) {
        
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
    
    public int getMovie_id() {
        return movie_id;
    }
    
    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public int getRelease_year() {
        return release_year;
    }
    
    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public int getOccurence() {
        return occurence;
    }
    
    public void setOccurence(int occurence) {
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
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        this.rating = rating;
    }
    
    public int getRating_votes() {
        return rating_votes;
    }
    
    public void setRating_votes(int rating_votes) {
        this.rating_votes = rating_votes;
    }
    
    public BigDecimal getBudget() {
        return budget;
    }
    
    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Movie) {
            Movie movie = (Movie) obj;
            return movie_id == movie.movie_id;
        }
        return false;
    }
    
}