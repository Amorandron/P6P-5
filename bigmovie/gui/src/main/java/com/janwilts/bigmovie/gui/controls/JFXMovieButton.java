package com.janwilts.bigmovie.gui.controls;

import com.janwilts.bigmovie.gui.models.Movie;
import com.jfoenix.controls.JFXButton;

public class JFXMovieButton extends JFXButton {
    private Movie movie;
    
    public JFXMovieButton(Movie movie) {
        super(movie.getTitle() + " (" + movie.getRelease_year() + ")");
        this.movie = movie;
    }
    
    public Movie getMovie() {
        return movie;
    }
}
