package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.rivescript.RiveScript;

public class MoreInfoSubroutine extends Routine {
    public MoreInfoSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        String response = "";

        Integer position = Integer.valueOf(args[0]);

        if(mainFocus.get(position) != null) {
            Object object = mainFocus.get(position);
            if(object instanceof Movie)
                response += new MoreMovieInfoSubroutine(bot, mainFocus).call(rs, new String[] {"movie", position.toString()});
        }

        return response;
    }
}
