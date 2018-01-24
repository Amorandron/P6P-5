package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.models.Soundtrack;
import com.janwilts.bigmovie.chatbot.models.SoundtrackMovies;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SoundTrackUsedSubroutine extends Routine {

    public SoundTrackUsedSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {

        APIRequester apiRequester = new APIRequester(SoundtrackMovies.class);
        StringBuilder result = new StringBuilder();
        LinkedHashMap<Integer, Movie> printMap = new LinkedHashMap<>();


        try {
            SoundtrackMovies soundtrackMovies = (SoundtrackMovies)apiRequester.getFromAPI("/q/a21");

            PrintUtils.blockprint(soundtrackMovies.getSoundtrack().getSong());

            int i = 0;

            result.append("Song:\n");
            result.append(PrintUtils.getBlock());
            result.append("\n");

            result.append("Movies:\n");

            while (i < soundtrackMovies.getMovies().size()){
                focusedMovies.put(i+1, soundtrackMovies.getMovies().get(i));
                printMap.put(i+1, soundtrackMovies.getMovies().get(i));

                if(i % 59 == 0 && i != 0) {
                    result.append(PrintUtils.movieListPrint(printMap));
                    result.append("||");

                    printMap.clear();
                }

                i++;
            }

            setMainFocus(focusedMovies);

            return result.toString();
        }
        catch(IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
