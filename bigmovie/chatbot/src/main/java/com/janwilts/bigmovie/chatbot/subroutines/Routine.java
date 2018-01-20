package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.models.Actor;
import com.janwilts.bigmovie.chatbot.models.Movie;

import java.util.HashMap;

public abstract class Routine {
    protected static HashMap<Integer, Movie> focusedMovies = new HashMap<>();
    protected static HashMap<Integer, Actor> focusedActors = new HashMap<>();
}
