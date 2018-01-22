package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.rivescript.RiveScript;

public class MovieEarnedSubroutine extends Routine {

    public MovieEarnedSubroutine(DiscordBot bot) {
        super(bot);
    }

    @Override
    public String call(RiveScript rs, String[] args) {
//        String result = "ERROR, NO DATA FOUND";

        //TODO get data from database and process it. The function works, as seen by the commented code below
        
//                String type = args[0];
//                if (type.equals("most")) {
//                    result = "The return of the dogs";
//                }
//                if (type.equals("least")) {
//                    result = "The return of the cats";
//                }
//                if (args.length > 1) {
//                    result += " #2";
//                }
        return "NYI";
    }
}
