package com.janwilts.bigmovie.chatbot.models;

public class Genre implements Model{

    private Integer genre_id;

    private String genre;

    public Genre(Integer genre_id, String genre) {
        this.genre_id = genre_id;
        this.genre = genre;
    }

    public Integer getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(Integer genre_id) {
        this.genre_id = genre_id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
