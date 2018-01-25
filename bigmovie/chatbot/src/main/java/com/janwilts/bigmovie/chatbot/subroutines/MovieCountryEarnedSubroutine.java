package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Country;
import com.janwilts.bigmovie.chatbot.models.Gross;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;

import java.util.ArrayList;
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

        APIRequester requester = new APIRequester(Country.class);

        List<Country> apiCountry = new ArrayList<>();
        List<Gross> apiGross;

        String url = "/q/d1";

        String quantum = args[0];

        //check if searched on most or least earned
        if(quantum.contains("most")){
            url += "/most";
        }else if(quantum.contains("least")){
            url += "/least";
        }

        //check if period given and set period
        if(args.length > 1){
            String period = args[1];

            if(period.equals("year")){
                url += "?period=365";
            }else if(period.equals("month")){
                url += "?period=30";
            }else if(period.equals("week")){
                url += "?period=7";
            }

            //request api
            try {
                requester = new APIRequester(Country.class);

                apiCountry = requester.getArrayFromAPI(url);

            }catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                apiCountry = requester.getArrayFromAPI(url);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        //process response api
        for(int i = 0; i < apiCountry.size(); i++) {
            focusedCountries.put(i + 1, apiCountry.get(i));
        }
        result.append(PrintUtils.countryListPrint(focusedCountries));

        return result.toString();
    }
}
