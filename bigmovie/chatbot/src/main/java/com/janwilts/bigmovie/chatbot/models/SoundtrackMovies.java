package com.janwilts.bigmovie.chatbot.models;

import java.util.ArrayList;
import java.util.List;

public class SoundtrackMovies {

    private Soundtrack soundtrack;
    private List<Movie> movies;

    public SoundtrackMovies() {
    }

    public SoundtrackMovies(Soundtrack soundtrack, List<Movie> movies) {

        this.soundtrack = soundtrack;
        this.movies = movies;
    }

    public Soundtrack getSoundtrack() {
        return soundtrack;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setSoundtrack(Soundtrack soundtrack) {
        this.soundtrack = soundtrack;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
