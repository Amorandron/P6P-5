package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.models.Soundtrack;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SvenMark & Everdien
 */
public class SoundTrackUsedSubroutine extends Routine {

    public SoundTrackUsedSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        //TODO: test if it works correctly
        String type = args[0];

        if(type.equals("all")){
            return PrintUtils.movieListPrint(focusedMovies);
        }

        focusedCountries.clear();
        StringBuilder result = new StringBuilder();

        APIRequester requester = new APIRequester();

        List<List> api = null;
        List<Soundtrack> soundtrack = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        if(type.contains("least")){
            return "You really don't want to know!";
        }

        try {
            api = requester.getArrayFromAPI("/q/a21");

            soundtrack = api.get(0);
            movies = api.get(1);

        }catch(Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < movies.size(); i++) {
            focusedMovies.put(i + 1, movies.get(i));
        }

        HashMap<Integer, Movie> top = new HashMap<>();
        for(int i = 1; i <= 10; i++) {
            top.put(i, focusedMovies.get(i));
        }

        PrintUtils.blockprint(String.format("Song: %s", soundtrack.get(0).getSong()));
        PrintUtils.blockprint(String.format("There are %s movies, to see them all type: all", focusedMovies.size()));
        result.append(PrintUtils.getBlock());
        result.append(PrintUtils.movieListPrint(top));

        return result.toString();
    }
}
