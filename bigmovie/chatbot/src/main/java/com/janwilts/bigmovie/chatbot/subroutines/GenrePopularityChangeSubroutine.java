package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.rivescript.RiveScript;

/**
 * @author Everdien
 */
public class GenrePopularityChangeSubroutine extends Routine {

    public GenrePopularityChangeSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "It changes.";

        //TODO get request from api.

        return result;
    }
}
