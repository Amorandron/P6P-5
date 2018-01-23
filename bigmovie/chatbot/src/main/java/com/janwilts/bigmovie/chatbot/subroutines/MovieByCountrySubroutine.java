package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Country;
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

        focusedCountries.clear();
        StringBuilder result = new StringBuilder();

        List<Country> api = null;

        APIRequester requester = new APIRequester(Country.class);

        String search = args[0];

        try {
            api = requester.getArrayFromAPI("/q/movies-by-country/?country=" + args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Country> currentCountries = new ArrayList<>();
        for(int i = 1; i < api.size(); i++) {
            focusedCountries.put(i, api.get(i));
        }

        result.append(PrintUtils.countryListPrint(focusedCountries));

        return result.toString();
    }
}
