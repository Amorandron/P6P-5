package com.ykapps.p6p.models;

public class Genre {

    private int genre_id;

    private String genre;

    public Genre(int genre_id, String genre) {
        this.genre_id = genre_id;
        this.genre = genre;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public String getGenre() {
        return genre;
    }
}
