package com.janwilts.bigmovie.chatbot.util;

import com.janwilts.bigmovie.chatbot.models.Actor;
import com.janwilts.bigmovie.chatbot.models.Country;
import com.janwilts.bigmovie.chatbot.models.Movie;

import java.util.HashMap;
import java.util.Map;

public class PrintUtils {
    private static StringBuilder block = new StringBuilder();

    public static void blockchar(String input) {
        block.append(input);
    }

    public static void blockprint(String input) {
        block.append(String.format("%s\n", input));
    }

    public static String getBlock() {
        String output = String.format("```css\n%s\n```", block.toString());
        block = new StringBuilder();
        return output;
    }

    public static String movieListPrint(HashMap<Integer, Movie> movies) {
        for (Map.Entry<Integer, Movie> set : movies.entrySet()) {
            Movie currentMovie = set.getValue();
            if (currentMovie.getTitle() == null) {
                blockprint(String.format("%d.\t %s", set.getKey(), currentMovie.getTitle()));
            } else {
                blockprint(String.format("%d.\t %s (%d)", set.getKey(), currentMovie.getTitle(), currentMovie.getRelease_year()));
            }
        }
        return getBlock();
    }

    public static String actorListPrint(HashMap<Integer, Actor> actors) {
        for (Map.Entry<Integer, Actor> entry : actors.entrySet()) {
            Actor currentActor = entry.getValue();
            String death_date = "-";
            String birth_date = "-";
            if(currentActor.getBirth_date() != null) {
                birth_date = currentActor.getBirth_date().toString();
            }
            if(currentActor.getDeath_date() != null) {
                death_date = currentActor.getDeath_date().toString();
            }
            blockprint(String.format("%d.\t %s %s %s %s", entry.getKey(), currentActor.getName(), currentActor.getGender(), birth_date, death_date));
        }
        return getBlock();
    }

    public static String countryListPrint(HashMap<Integer, Country> countries) {
        for (Map.Entry<Integer, Country> set : countries.entrySet()) {
            Country currentCountry = set.getValue();
            if (currentCountry.getCountry() == null) {
                continue;
            }
            blockprint(String.format("%d.\t %s", set.getKey(), currentCountry.getCountry()));
        }
        return getBlock();
    }
}
