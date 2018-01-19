package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

import java.io.IOException;

/**
 * @author Everdien
 */
public class MovieCostSubroutine implements Subroutine {

    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "...";

        Movie api = null;

        APIRequester requester = new APIRequester(Movie.class);
        try {
            api = (Movie) requester.getFromAPI("/q/a21/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String type = args[0];
        if(type.contains("most")){
            result = String.format("%s (%d)", api.getTitle(), api.getRelease_year());
        }else if(type.contains("least") || type.equals("cheapest")){
            result = "least";
        }

        return result;
    }
}
