package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Everdien & Jan
 */
public class MovieCostSubroutine extends Routine implements Subroutine {

    @Override
    public String call(RiveScript rs, String[] args) {
        focusedMovies.clear();
        StringBuilder result = new StringBuilder();

        List<Movie> api = null;

        APIRequester requester = new APIRequester(Movie.class);

        String type = args[0];
        if(type.contains("most")){
            try {
                api = requester.getArrayFromAPI("/q/a7/most");
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Movie> currentMovies = new ArrayList<>();
            for(int i = 1; i < api.size(); i++) {
                focusedMovies.put(i, api.get(i));
            }
            result.append(PrintUtils.movieListPrint(focusedMovies));
        }else if(type.contains("least") || type.equals("cheapest")){
            try {
                api = requester.getArrayFromAPI("/q/a7/least");
            } catch (Exception e) {
                e.printStackTrace();
            }
            for(int i = 0; i < api.size(); i++) {
                focusedMovies.put(i + 1, api.get(i));
            }
            result.append(PrintUtils.movieListPrint(focusedMovies));
        }

        return result.toString();
    }
}
