package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;

import java.util.List;

public class MovieInfoSubroutine extends Routine {

    public MovieInfoSubroutine(DiscordBot bot) { super(bot); }

    @Override
    public String call(RiveScript rs, String[] args) {
        StringBuilder result = new StringBuilder();

        if (args.length <= 0) {
            return "";
        }

        List<Movie> api = null;

        APIRequester requester = new APIRequester(Movie.class);

        String search = args[0];
        try {
            api = requester.getArrayFromAPI("/q/movie/?movie=" + search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0; i < api.size(); i++) {
            focusedMovies.put(i + 1, api.get(i));
        }
        result.append(PrintUtils.movieListPrint(focusedMovies));

        return result.toString();
    }
}
