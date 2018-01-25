package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Actor;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.rivescript.RiveScript;

/**
 * @author Everdien
 */
public class ActorActSubroutine extends Routine {
    public ActorActSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        focusedActors.clear();
        StringBuilder result = new StringBuilder();

        Actor apiActor = null;

        APIRequester requester = new APIRequester(Actor.class);

        String url = "/q/a15";

        String type = args[0];
        String quantum = args[1];
        String rating = args[2];

        if(quantum.contains("most")){
            url += "/most";
        }else if(quantum.contains("least")){
            url += "/least";
        }

        if(rating.contains("worst")){
            url += "?rating=1";
        }else if(rating.contains("best")){
            url += "?rating=10";
        }

        if(type.contains("actor") && type.contains("actress")){
            try {
                apiActor = (Actor)requester.getFromAPI(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(type.contains("actor")){
            try {
                apiActor = (Actor)requester.getFromAPI(url + "&gender=M");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(type.contains("actress")){
            try {
                apiActor = (Actor)requester.getFromAPI(url + "&gender=F");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        focusedActors.put(1, apiActor);

        return new MoreActorInfoSubroutine(bot, focusedActors).call(rs, new String[]{"1"});
    }
}
