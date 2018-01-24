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
            requester.getFromAPI(String.format("/q/b4?country=%s", country));
            image = requester.getImageFromAPI("/plots/b4.png", "b4.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileOutput(image);
    }
}
