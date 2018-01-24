package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Focusable;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.rivescript.RiveScript;

public class MoreInfoSubroutine extends Routine {
    public MoreInfoSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        Integer position = Integer.valueOf(args[0]);

        Object o = mainFocus.get(position);

        if(mainFocus.get(position) != null) {
            Focusable object = (Focusable) mainFocus.get(position);

            if(object instanceof Movie)
                new MoreMovieInfoSubroutine(bot, mainFocus).call(rs, new String[] {"movie", position.toString()});
        }

        return "";
    }
}
