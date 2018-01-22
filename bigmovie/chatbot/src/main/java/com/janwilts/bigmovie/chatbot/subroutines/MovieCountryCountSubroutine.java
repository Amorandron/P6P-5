package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.rivescript.RiveScript;

import java.io.File;
import java.io.IOException;

/**
 * @author Jan
 */
public class MovieCountryCountSubroutine extends Routine {

    public MovieCountryCountSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        String country = args[0];

        File image = null;

        APIRequester requester = new APIRequester(String.class);
        try {
            requester.getFromAPI("/q/b5");
            image = requester.getImageFromAPI(String.format("/plots/b5.png?country=%s", country), "b5.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileOutput(image);
    }
}
