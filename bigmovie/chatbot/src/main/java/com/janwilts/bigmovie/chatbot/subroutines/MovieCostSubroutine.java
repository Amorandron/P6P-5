package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

import java.util.List;

/**
 * @author Everdien
 */
public class MovieCostSubroutine implements Subroutine {

    @Override
    public String call(RiveScript rs, String[] args) {
        StringBuilder result = new StringBuilder();

        List<Movie> api = null;

        APIRequester requester = new APIRequester(Movie.class);
        try {
            api = requester.getArrayFromAPI("/q/a7/");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String type = args[0];
        if(type.contains("most")){
            int index = 1;

            for(Movie m : api) {
                result.append(String.format("```%d. %s (%d)```", index, m.getTitle(), m.getRelease_year()));
                index++;
            }
        }else if(type.contains("least") || type.equals("cheapest")){
            result = new StringBuilder("least");
        }

        return result.toString();
    }
}
