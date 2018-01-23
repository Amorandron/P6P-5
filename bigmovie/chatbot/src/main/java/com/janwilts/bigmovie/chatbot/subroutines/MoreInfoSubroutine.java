package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Country;
import com.janwilts.bigmovie.chatbot.models.Genre;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

import java.util.List;

/**
 * @author Everdien
 */
public class MoreInfoSubroutine extends Routine {

    public MoreInfoSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        StringBuilder result = new StringBuilder();
        List<String> genre = null;
        List<String> country = null;

        List<Genre> apiGenre = null;
        List<Country> apiCountry = null;

        String type = args[0];
        int position = Integer.parseInt(args[1]);
        if(type.equals("movie")){
            Movie movie = focusedMovies.get(position);

            APIRequester requester = new APIRequester(Genre.class);

            try {
                apiGenre = requester.getArrayFromAPI("/genres?movie_id=" + movie.getMovie_id());
            } catch (Exception e) {
                e.printStackTrace();
            }

            for(int i = 0; i < apiGenre.size(); i++) {
                genre.add(apiGenre.get(i).getGenre());
            }

            requester = new APIRequester(Country.class);

            try {
                apiCountry = requester.getArrayFromAPI("/countries?movie_id=" + movie.getMovie_id());
            } catch (Exception e) {
                e.printStackTrace();
            }

            for(int i = 0; i < apiCountry.size(); i++) {
                country.add(apiCountry.get(i).getCountry());
            }

            result.append(String.format("Title: %s\nYear: %d\nMpaa-rating: %s\nRating: %d\nBudget: %f\nCountry %s\nGenre: %s", movie.getTitle(), movie.getRelease_year(), movie.getMpaa_rating(), movie.getRating(), movie.getBudget(), String.join(",", country), String.join(",", genre)));
        }

        return result.toString();
    }
}
