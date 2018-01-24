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
        String type = args[0];

        if(type.equals("least")) {
            return "You really don't want to know!";
        }

        APIRequester apiRequester = new APIRequester(SoundtrackMovies.class);
        StringBuilder result = new StringBuilder();

        focusedMovies.clear();
        LinkedHashMap<Integer, Movie> printMap = new LinkedHashMap<>();

        try {
            SoundtrackMovies soundtrackMovies = (SoundtrackMovies)apiRequester.getFromAPI("/q/a21");

            PrintUtils.blockprint(soundtrackMovies.getSoundtrack().getSong());

            int i = 0;

            result.append("Song:\n");
            result.append(PrintUtils.getBlock());
            result.append("\n");

            result.append("Movies:\n");

            if(type.equals("all")) {
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
            }
            else {
                for(int i2 = 0; i2 < 10; i2++) {
                    focusedMovies.put(i2+1, soundtrackMovies.getMovies().get(i2));
                }

                result.append(PrintUtils.movieListPrint(focusedMovies));

                PrintUtils.blockprint(String.format("There are %s movies, to see them all type: 'show me all movies this song was used in'", soundtrackMovies.getMovies().size()));
                result.append(PrintUtils.getBlock());
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
