package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Actor;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;

import java.util.LinkedHashMap;
import java.util.List;

public class ActorRoleSubroutine extends Routine {
    public ActorRoleSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        StringBuilder result = new StringBuilder();
        StringBuilder firstname = new StringBuilder();
        StringBuilder lastname = new StringBuilder();
        StringBuilder search = new StringBuilder();
        focusedMovies.clear();
        focusedActors.clear();
        LinkedHashMap<Integer, Movie> printMap = new LinkedHashMap<>();
        if (args.length <= 0) {
            return "Send args";
        }

        if (args[0].contains("actor")) {
            // Search for movies with actor x

            List<Movie> api = null;

            APIRequester requester = new APIRequester(Movie.class);
            if(args.length > 2) {
                //Add second name
                for (int i = 1; i < args.length - 1; i++) {
                    firstname.append(args[i]);
                    if (args.length - 2 != i) {
                        firstname.append(" ");
                    }
                }
                lastname.append(args[args.length - 1]);
            } else {
                return "You need to specify a first- and lastname";
            }

            try {
                api = requester.getArrayFromAPI(String.format("/q/d2/?lastname=%s&firstname=%s", lastname, firstname));
            } catch (Exception e) {
                e.printStackTrace();
            }
            int i = 0;

            result.append("Actor:\n");
            result.append(lastname);
            result.append(", ");
            result.append(firstname);
            result.append("\n");

            result.append("Movies:\n");
            if(api.size() == 0) {
                result.append("No movies");
                return result.toString();
            }

            if(args[0].contains("actorall")) {
                while (i < api.size()) {
                    focusedMovies.put(i + 1, api.get(i));
                    printMap.put(i + 1, api.get(i));

                    if (i % 7 == 0 && i != 0) {
                        result.append(PrintUtils.movieListPrint(printMap));
                        result.append("||");

                        printMap.clear();

                    }

                    i++;
                }
            }
            else {
                for(int i2 = 0; i2< 10; i2++) {
                    focusedMovies.put(i2+1, api.get(i2));
                }

                result.append(PrintUtils.movieListPrint(focusedMovies));
                String fullname = firstname + " " + lastname;
                PrintUtils.blockprint(String.format("There are %s movies, to se them all type: 'show me all the movies with %s", api.size(), fullname));
                result.append(PrintUtils.getBlock());



            }
        }
        else if (args[0].contains("movie") && (args.length > 1)) {
            // Give actors who play in movie x

            List<Actor> api = null;

            APIRequester requester = new APIRequester(Actor.class);

            search.append(args[1].toLowerCase());
            if(args.length > 2) {
                for (int i = 2; i < args.length; i++) {
                    search.append(" ");
                    search.append(args[i].toLowerCase());
                }
            }

            try {
                api = requester.getArrayFromAPI(String.format("/q/d2-reverse/?movie=%s", search));
            } catch (Exception e) {
                e.printStackTrace();
            }
            for(int i = 0; i < api.size(); i++) {
                focusedActors.put(i + 1, api.get(i));
            }
            result.append(PrintUtils.actorListPrint(focusedActors));
        }
        else {
            result.append("I didn't understand");
        }

        return result.toString();
    }
}
