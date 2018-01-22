package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.rivescript.RiveScript;

public class MovieRatingMpaaSubroutine extends Routine {
    public MovieRatingMpaaSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "";

        //TODO get request from api.

        if (args.length == 0) {
            return result;
        }

        for (String arg : args) {
            result += arg;
            result += " ";
        }

        return result;
    }
}
