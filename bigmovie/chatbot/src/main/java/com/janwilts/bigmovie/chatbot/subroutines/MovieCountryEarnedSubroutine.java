package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.rivescript.RiveScript;

/**
 * @author Everdien
 */
public class MovieCountryEarnedSubroutine extends Routine {

    public MovieCountryEarnedSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "...";

        //TODO get request from api.

        String quantum = args[0];
        if(quantum.contains("most")){
            result = "most";
        }else if(quantum.contains("least")){
            result = "least";
        }

        if(args.length > 1){
            String period = args[1];

            if(period.equals("year")){
                result += ", year";
            }else if(period.equals("month")){
                result += ", month";
            }else if(period.equals("week")){
                result +=", week";
            }
        }

        return result;
    }
}
