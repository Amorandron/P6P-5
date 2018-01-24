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

        if (args.length <= 0) {
            return "";
        }

        List<Actor> api = null;

        APIRequester requester = new APIRequester(Actor.class);

        String search = args[0];
        try {
            api = requester.getArrayFromAPI("/q/actor/?actor=" + search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0; i < api.size(); i++) {
            focusedActors.put(i + 1, api.get(i));
        }
        result.append(PrintUtils.actorListPrint(focusedActors));
        setMainFocus(focusedActors);

        return result.toString();
    }
}
