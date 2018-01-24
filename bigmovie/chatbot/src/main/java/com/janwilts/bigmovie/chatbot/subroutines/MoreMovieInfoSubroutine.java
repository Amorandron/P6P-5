package com.janwilts.bigmovie.chatbot.subroutines;

import com.google.common.base.Strings;
import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Country;
import com.janwilts.bigmovie.chatbot.models.Genre;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Everdien & Jan
 */
public class MoreMovieInfoSubroutine extends Routine {

    private Map<?, ?> source;

    public MoreMovieInfoSubroutine(DiscordBot bot, Map<?, ?> source) {
        super(bot);
        this.source = source;
    }

    public MoreMovieInfoSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        StringBuilder result = new StringBuilder();
        List<String> genre = new ArrayList<>();
        List<String> country = new ArrayList<>();

        List<Genre> apiGenre = null;
        List<Country> apiCountry = null;

        String type = args[0];
        int position = Integer.parseInt(args[1]);
        if(type.equals("movie")){
            Movie movie = null;
            if(source != null)
                movie = (Movie) source.get(position);
            else
                movie = focusedMovies.get(position);

            APIRequester requester = new APIRequester(Genre.class);

            try {
                apiGenre = requester.getArrayFromAPI("/genres?movie_id=" + movie.getMovie_id());
            } catch (Exception e) {
                e.printStackTrace();
            }

            for(Genre g : apiGenre)
                genre.add(g.getGenre());

            requester = new APIRequester(Country.class);

            try {
                apiCountry = requester.getArrayFromAPI("/countries?movie_id=" + movie.getMovie_id());
            } catch (Exception e) {
                e.printStackTrace();
            }

            for(Country c : apiCountry)
                country.add(c.getCountry());

            PrintUtils.blockprint(String.format("Title: %s", movie.getTitle()));
            PrintUtils.blockprint("----------------");
            if(movie.getRelease_year() != null || movie.getRelease_year() != 0)
                PrintUtils.blockprint(String.format("Year: %d", movie.getRelease_year()));
            if(!Strings.isNullOrEmpty(movie.getMpaa_rating()))
                PrintUtils.blockprint(String.format("Mpaa-rating: %s", movie.getMpaa_rating()));
            if(movie.getBudget() != null || !movie.getBudget().equals(new BigDecimal(0)))
                PrintUtils.blockprint(String.format("Budget: %s", movie.getBudget()));
            if(country.size() > 0)
                PrintUtils.blockprint(String.format("\nCountries: %s", String.join(", ", country)));
            if(genre.size() > 0)
                PrintUtils.blockprint(String.format("\nGenres: %s", String.join(", ", genre)));
        }

        return PrintUtils.getBlock();
    }
}
