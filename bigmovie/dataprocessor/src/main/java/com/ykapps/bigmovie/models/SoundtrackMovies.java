package com.ykapps.bigmovie.models;

import java.util.List;

public class SoundtrackMovies {

    private Soundtrack soundtrack;
    private List<Movie> movies;

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
}