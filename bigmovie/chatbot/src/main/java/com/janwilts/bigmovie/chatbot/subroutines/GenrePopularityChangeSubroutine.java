package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.rivescript.RiveScript;

import java.io.File;
import java.io.IOException;

/**
 * @author Jan
 */
public class GenrePopularityChangeSubroutine extends Routine {

    public GenrePopularityChangeSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        File image = null;

        APIRequester requester = new APIRequester(String.class);
        try {
            requester.getFromAPI("/q/b5");
            image = requester.getImageFromAPI("/plots/b5.png", "b5.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileOutput(image);
    }
}
