package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Country;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;

import java.util.List;

/**
 * @author Everdien
 */
public class MovieCountryEarnedSubroutine extends Routine {

    public MovieCountryEarnedSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        focusedCountries.clear();
        StringBuilder result = new StringBuilder();

        List<Country> apiCountry = null;

        APIRequester requester = new APIRequester(Country.class);

        String url = "/q/d1";

        String quantum = args[0];

        if(quantum.contains("most")){
            url += "/most";
        }else if(quantum.contains("least")){
            url += "/least";
        }

        if(args.length > 1){
            String period = args[1];

            if(period.equals("year")){
                url += "?period=year";
            }else if(period.equals("month")){
                url += "?period=month";
            }else if(period.equals("week")){
                url += "?period=week";
            }
        }

        try {
            apiCountry = requester.getArrayFromAPI(url);
        }catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < apiCountry.size(); i++) {
            focusedCountries.put(i + 1, apiCountry.get(i));
        }
        result.append(PrintUtils.countryListPrint(focusedCountries));

        return result.toString();
    }
}
