package com.ykapps.bigmovie.models;

public class Genre {

    private Integer genre_id;

    private String genre;

    public Genre(Integer genre_id, String genre) {
        this.genre_id = genre_id;
        this.genre = genre;
    }

    public Integer getGenre_id() {
        return genre_id;
    }

    public String getGenre() {
        return genre;
    }
}
