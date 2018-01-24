package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Country;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sven Mark
 */

public class MovieByCountrySubroutine extends Routine {

    public MovieByCountrySubroutine(DiscordBot bot) { super(bot);}

    @Override
    public String call(RiveScript rs, String[] args) {
        if(args.length <= 0) {return "Missing argument";}

        APIRequester requester = new APIRequester(Movie.class);

        try {
            List api = requester.getArrayFromAPI("/q/movies-by-country?country=" + args[0]);
            return String.valueOf(api.size());
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred.";
        }
    }
}
