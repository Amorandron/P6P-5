package com.janwilts.bigmovie.chatbot.models;

public class Soundtrack implements Model{

    private Integer soundtrack_id;

    private Integer movie_id;

    private String song;

    public Soundtrack() {
    }

    public Soundtrack(Integer soundtrack_id, Integer movie_id, String song) {
        this.soundtrack_id = soundtrack_id;
        this.movie_id = movie_id;
        this.song = song;
    }

    public Integer getSoundtrack_id() {
        return soundtrack_id;
    }

    public void setSoundtrack_id(Integer soundtrack_id) {
        this.soundtrack_id = soundtrack_id;
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }
}
