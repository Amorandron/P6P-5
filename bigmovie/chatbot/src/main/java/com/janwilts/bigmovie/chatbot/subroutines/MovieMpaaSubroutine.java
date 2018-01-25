package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jan
 */
public class MovieMpaaSubroutine extends Routine {
    private static final String[] ratings = {
        "G", "NC-17", "PG", "PG-13", "R"
    };

    public MovieMpaaSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "";

        if (args[0].equals("rated")) {
            APIRequester requester = new APIRequester(Movie.class);
            List<Movie> movies = null;
            try {
                movies = requester.getArrayFromAPI("/q/movie?movie=" + args[1]);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            for(Movie m : movies) {
                PrintUtils.blockprint(String.format("%s (%d): %s", m.getTitle(), m.getRelease_year(), m.getMpaa_rating()));
            }
            result += PrintUtils.getBlock();
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
            APIRequester requester = new APIRequester(Object.class);
            Object objects = null;
            try {
                requester.getFromAPI("/q/c2");
                objects = requester.getFromAPI("/q/c2/validation");
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<List<Double>> matrix = (List<List<Double>>) ((ArrayList<Object>) objects).get(0);
            Double accuracy = (Double) ((ArrayList<Object>) objects).get(1);

            for(int i = 0; i < ratings.length + 1; i++) {
                if(i == 0) {
                    PrintUtils.blockchar(StringUtils.center("", 8));
                    for(String rating : ratings)
                        PrintUtils.blockchar(StringUtils.center(rating, 8));
                    PrintUtils.blockchar("\n");
                }
                else {
                    PrintUtils.blockchar(StringUtils.center(ratings[i - 1], 8));
                    for(int j = 0; j < ratings.length; j++)
                        PrintUtils.blockchar(StringUtils.center(matrix.get(i).get(j).toString(), 8));
                    PrintUtils.blockchar("\n");
                }
            }

            result += "Confusion matrix:";
            result += PrintUtils.getBlock();
            result += "||";
            result += "Accuracy:";
            PrintUtils.blockprint(accuracy.toString());
            result += PrintUtils.getBlock();

        }
        return result;
    }
}
