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

        int position = Integer.parseInt(args[0]);
        Movie movie = null;
        if (source != null)
            movie = (Movie) source.get(position);
        else
            movie = focusedMovies.get(position);

        APIRequester requester = new APIRequester(Genre.class);

        //get all genres of the selected movie
        try {
            apiGenre = requester.getArrayFromAPI("/genres?movie_id=" + movie.getMovie_id());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Genre g : apiGenre)
            genre.add(g.getGenre());

        requester = new APIRequester(Country.class);

        //get all countries of the selected movie
        try {
            apiCountry = requester.getArrayFromAPI("/countries?movie_id=" + movie.getMovie_id());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Country c : apiCountry)
            country.add(c.getCountry());

        //make large budgets more readable by adding commas
        String budget = movie.getBudget().toString();
        String decimal = budget.substring(budget.indexOf("."));
        String whole = budget.substring(0, budget.indexOf(".") - 1);
        StringBuilder newWhole = new StringBuilder();

        int counter = 0;

        for(int i = whole.length() - 1; i >= 0; i--) {
            if(counter == 3) {
                newWhole.append(",");
                counter = 0;
            }
            newWhole.append(whole.charAt(i));
            counter++;
        }

        newWhole.reverse();

        if(decimal.equals(".00"))
            decimal = ".-";

        String countryName = country.size() > 1 ? "Countries" : "Country";
        String genreName = country.size() > 1 ? "Genres" : "Genre";

        budget = newWhole.toString() + decimal;

        //formatting output
        PrintUtils.blockprint(String.format("Title: %s", movie.getTitle()));
        PrintUtils.blockprint("----------------");
        if (movie.getRelease_year() != null || movie.getRelease_year() != 0)
            PrintUtils.blockprint(String.format("Year: %d", movie.getRelease_year()));
        if (movie.getRating() != null)
            PrintUtils.blockprint(String.format("Rating: %3.1f (%d votes)", movie.getRating(), movie.getRating_votes()));
        if (!Strings.isNullOrEmpty(movie.getMpaa_rating()))
            PrintUtils.blockprint(String.format("Mpaa-rating: %s", movie.getMpaa_rating()));
        if (movie.getBudget() != null || !movie.getBudget().equals(new BigDecimal(0)))
            PrintUtils.blockprint(String.format("Budget: $%s", budget));
        if (country.size() > 0)
            PrintUtils.blockprint(String.format("\n%s: %s", countryName, String.join(", ", country)));
        if (genre.size() > 0)
            PrintUtils.blockprint(String.format("\n%s: %s", genreName, String.join(", ", genre)));


        return PrintUtils.getBlock();
    }
}
