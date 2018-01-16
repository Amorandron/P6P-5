package com.ykapps.bigmovie.models;

public class Soundtrack {

    private Integer soundtrack_id;

    private Integer movie_id;

    private String song;

    public Soundtrack(Integer soundtrack_id, Integer movie_id, String song) {
        this.soundtrack_id = soundtrack_id;
        this.movie_id = movie_id;
        this.song = song;
    }

    public Integer getSoundtrack_id() {
        return soundtrack_id;
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public String getSong() {
        return song;
    }
}
