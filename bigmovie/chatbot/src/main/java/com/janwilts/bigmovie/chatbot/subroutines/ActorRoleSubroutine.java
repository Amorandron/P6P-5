package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Actor;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;

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
        if (args.length <= 0) {
            return "Send args";
        }

        if (args[0].contains("actor") && (args.length > 1)) {
            // Search for movies with actor x

            List<Movie> api = null;

            APIRequester requester = new APIRequester(Movie.class);
            if(args.length > 2) {
                //Add second name
                for (int i = 2; i < args.length; i++) {
                    lastname.append(args[i]);
                    if (args.length - 1 != i) {
                        lastname.append(" ");
                    }
                }
            }
            // Add firstname
            firstname.append(args[1]);

            try {
                api = requester.getArrayFromAPI(String.format("/q/d2/?lastname=%s&firstname=%s", lastname, firstname));
            } catch (Exception e) {
                e.printStackTrace();
            }
            for(int i = 0; i < api.size(); i++) {
                focusedMovies.put(i + 1, api.get(i));
            }
            result.append(PrintUtils.movieListPrint(focusedMovies));
        }
        else if (args[0].contains("movie") && (args.length > 1)) {
            // Give actors who play in movie x

            List<Actor> api = null;

            APIRequester requester = new APIRequester(Actor.class);

            search.append(args[1]);
            for(int i = 2; i < args.length; i++) {
                search.append(" ");
                search.append(args[i]);

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
