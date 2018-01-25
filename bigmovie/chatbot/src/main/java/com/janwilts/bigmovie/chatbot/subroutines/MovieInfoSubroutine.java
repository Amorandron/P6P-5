package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public class MovieInfoSubroutine extends Routine {

    public MovieInfoSubroutine(DiscordBot bot) { super(bot); }

    @Override
    public String call(RiveScript rs, String[] args) {
        StringBuilder result = new StringBuilder();
        StringBuilder search = new StringBuilder();
        focusedMovies.clear();
        LinkedHashMap<Integer, Movie> printMap = new LinkedHashMap<>();

        if (args.length <= 0) {
            return "I didn't understand";
        }

        List<Movie> api = null;

        APIRequester requester = new APIRequester(Movie.class);

        for(int i = 1; i < args.length; i++) {
            search.append(args[i]);
            if (args.length - 1 != i) {
                search.append(" ");
            }
        }
        try {
            api = requester.getArrayFromAPI("/q/movie/?movie=" + search);
        } catch (Exception e) {
            e.printStackTrace();
        }

        result.append("Search: ");
        result.append(search);
        result.append("\n");
        result.append("Movies:\n");

        if(api.size() == 0) {
            result.append("No movies");
            return result.toString();
        }

        int i = 0;

        if(args[0].contains("all")) {
            while (i < api.size()) {
                focusedMovies.put(i + 1, api.get(i));
                printMap.put(i + 1, api.get(i));

                if (i % 6 == 0 && i != 0) {
                    result.append(PrintUtils.movieListPrint(printMap));
                    result.append("||");

                    printMap.clear();

                }

                i++;
            }
        }
        else {
            for(int i2 = 0; i2< 10; i2++) {
                focusedMovies.put(i2+1, api.get(i2));
            }

            result.append(PrintUtils.movieListPrint(focusedMovies));
            PrintUtils.blockprint(String.format("There are %s movies, to se them all type: 'show me all the movies with %s", api.size(), search));
            result.append(PrintUtils.getBlock());
        }

        setMainFocus(focusedMovies);

        return result.toString();
    }
}
