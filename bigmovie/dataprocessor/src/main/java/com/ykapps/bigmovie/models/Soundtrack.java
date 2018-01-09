package com.ykapps.bigmovie.models;

public class Soundtrack {

    private int soundtrack_id;

    private int movie_id;

    private String song;

    public Soundtrack(int soundtrack_id, int movie_id, String song) {
        this.soundtrack_id = soundtrack_id;
        this.movie_id = movie_id;
        this.song = song;
    }

    public int getSoundtrack_id() {
        return soundtrack_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getSong() {
        return song;
    }
}
