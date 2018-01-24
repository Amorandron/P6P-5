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
        StringBuilder search = new StringBuilder();
        if (args.length <= 0) {
            return "Send args";
        }

        if (args[0].contains("actor")) {
            // Search for movies with actor x

            List<Movie> api = null;

            APIRequester requester = new APIRequester(Movie.class);

            for(int i = 1; i < args.length; i++) {
                search.append(args[i]);
                search.append(" ");
            }

            result = search;

//            try {
//                api = requester.getArrayFromAPI("/q/d2/?actor=" + search);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            for(int i = 0; i < api.size(); i++) {
//                focusedMovies.put(i + 1, api.get(i));
//            }
//            result.append(PrintUtils.movieListPrint(focusedMovies));
        }
        else {
            // Give actors who play in movie x

            List<Actor> api = null;

            APIRequester requester = new APIRequester(Actor.class);

            for(int i = 1; i < args.length; i++) {
                search.append(args[i]);
                search.append(" ");
            }
//            try {
//                api = requester.getArrayFromAPI("/q/");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            for(int i = 0; i < api.size(); i++) {
//                focusedActors.put(i + 1, api.get(i));
//            }
//            result.append(PrintUtils.actorListPrint(focusedActors));

            result = search;
        }



        return result.toString();
    }
}
