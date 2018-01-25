package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.*;
import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.rivescript.RiveScript;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LUISCallSubroutine extends Routine{

    public LUISCallSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        APIRequester apiRequester = new APIRequester();

        try {
            String message = rs.getUservar(rs.currentUser(), "origMessage");
            message = message.substring(message.indexOf('>') + 1);

            LUISResponse response = apiRequester.getLUIS(message);

            LUISTopScoringIntent intent = response.getTopScoringIntent();
            List<String> requiredEntities = new ArrayList<>();

            if(intent.getScore() < 0.2 || intent.getIntent().equals("None")) {
                return "Sorry, I don't understand.";
            }

            switch(intent.getIntent()) {
                case("ActorAct"):
                    requiredEntities.add("ActorAndOrActress");
                    requiredEntities.add("MostLeast");
                    requiredEntities.add("WorstBest");

                    break;

                case("ActorInfo"):
                    requiredEntities.add("ActorName");
                    break;

                case("MoreActorInfo"):
                    requiredEntities.add("ActorId");
                    break;

                case("MoreMovieInfo"):
                    requiredEntities.add("MovieId");
                    break;

                case("MovieByCountry"):
                    requiredEntities.add("Country");
                    break;

                case("MovieCost"):
                    requiredEntities.add("MostLeast");
                    break;

                case("MovieCountryCount"):
                    requiredEntities.add("Country");
                    break;

                case("MovieCountryEarned"):
                    requiredEntities.add("MostLeast");
                    requiredEntities.add("Period");
                    break;

                case("MovieDetail"):
                    requiredEntities.add("MovieId");
                    break;

                case("MovieEarned"):
                    requiredEntities.add("Period");
                    requiredEntities.add("Country");
                    break;

                case("MovieInfo"):
                    requiredEntities.add("Movie");
                    break;

                case("MovieMPAA"):
                    intent.setIntent("MovieMpaa");

                    requiredEntities.add("Movie");
                    break;

                case("SoundTrackUsed"):
                    requiredEntities.add("MostLeast");
                    break;
            }

            for(String entityName : requiredEntities) {
                if(response.getEntities().stream().noneMatch(en -> en.getType().equals(entityName))) {
                    return "Sorry, I don't understand.";
                }
            }

            if(intent.getIntent().equals("Greet")) {
                return "Hello there!";
            }
            else if(intent.getIntent().equals("Goodbye")) {
                return "Bye!";
            }

            List<String> entityArgs = response.getEntities()
                    .stream()
                    .map(LUISEntity::getEntity)
                    .collect(Collectors.toList());

            try {
                String className = "com.janwilts.bigmovie.chatbot.subroutines." + intent.getIntent() + "Subroutine";
                Class currentSubroutine = Class.forName(className);
                Method callMethod = currentSubroutine.getMethod("call", RiveScript.class, String[].class);

                return (String)callMethod.invoke(
                        currentSubroutine.getConstructor(DiscordBot.class).newInstance(bot),
                        rs, entityArgs.toArray(new String[0]));
            }
            catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();

                return "An error occurred";
            }


        }
        catch (IOException e) {
            e.printStackTrace();

            return "An error occurred";
        }
    }

}
