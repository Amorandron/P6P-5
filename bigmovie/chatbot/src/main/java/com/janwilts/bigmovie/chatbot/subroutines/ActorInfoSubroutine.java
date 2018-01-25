package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Actor;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.janwilts.bigmovie.chatbot.util.PrintUtils;
import com.rivescript.RiveScript;

import java.util.List;

public class ActorInfoSubroutine extends Routine {

    public ActorInfoSubroutine(DiscordBot bot) {super (bot);}

    @Override
    public String call(RiveScript rs, String[] args) {
        super.call(rs, args);
        StringBuilder result = new StringBuilder();
        StringBuilder firstname = new StringBuilder();
        StringBuilder lastname = new StringBuilder();
        focusedActors.clear();

        if (args.length <= 0) {
            return "Send args";
        }

        List<Actor> api = null;

        APIRequester requester = new APIRequester(Actor.class);

        if(args.length > 1) {
            //Add second name
            for (int i = 0; i < args.length - 1; i++) {
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
            api = requester.getArrayFromAPI(String.format("/q/actor?lastname=%s&firstname=%s", lastname, firstname));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(api.size() == 0) {
            return "No results, try another name";
        }
        for(int i = 0; i < api.size(); i++) {
            focusedActors.put(i + 1, api.get(i));
        }
        result.append(PrintUtils.actorListPrint(focusedActors));
        setMainFocus(focusedActors);

        return result.toString();
    }
}
