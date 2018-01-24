package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.rivescript.RiveScript;

import java.io.File;
import java.io.IOException;

public class MovieMpaaSubroutine extends Routine {
    public MovieMpaaSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "";

        if (args[0].equals("rated")) {
            APIRequester requester = new APIRequester(Movie.class);
            Movie movie = null;
            try {
                movie = (Movie) requester.getFromAPI("/q/movie" + args[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            result += movie.getMpaa_rating();
        } else if (args[0].equals("prediction")) {

        } else if (args[0].equals("model")) {
            APIRequester requester = new APIRequester();
            try {
                requester.getFromAPI("/q/c2");
            } catch (IOException e) {
                e.printStackTrace();
            }

            File image = null;

            try {
                image = requester.getImageFromAPI("/plots/c2.png", "c2.png");
            } catch (IOException e) {
                e.printStackTrace();
            }
            result += fileOutput(image);
        } else if(args[0].equals("validation")) {
            APIRequester requester = new APIRequester(Object[].class);
            Object[] objects;
            try {
                objects = (Object[]) requester.getFromAPI("/q/c2");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
