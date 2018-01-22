package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.rivescript.RiveScript;

/**
 * @author Everdien
 */
public class MovieCountryCountSubroutine extends Routine {

    public MovieCountryCountSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "...";

        String country = args[0];
        result = country;

        if(args.length > 1){
            if(args[1].equals("time")){
                result += ", displayed in time";
            }
        }

        return result;
    }
}
