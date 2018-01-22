package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.rivescript.RiveScript;

import java.io.File;
import java.io.IOException;

/**
 * @author Everdien
 */
public class GetRoleDifficultSubroutine extends Routine {

    public GetRoleDifficultSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        File image = null;

        APIRequester requester = new APIRequester(String.class);
        try {
            requester.getFromAPI("/q/c4");
            image = requester.getImageFromAPI("/plots/c4.png", "c4.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileOutput(image);
    }
}
