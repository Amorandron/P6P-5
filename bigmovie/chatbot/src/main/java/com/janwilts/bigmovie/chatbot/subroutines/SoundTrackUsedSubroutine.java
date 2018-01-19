package com.janwilts.bigmovie.chatbot.subroutines;

import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

public class SoundTrackUsedSubroutine implements Subroutine {

    @Override
    public String call(RiveScript rs, String[] args) {

        String result = "";
        //TODO get data from api

        //arg[0] = order_by
        //arg[1] = movie

        String type = args[0];
        if(type.contains("most")){
            result = "most";
        }
        else if(type.contains("least")){
            result = "least";
        }
        if (args.length > 1) {
            String movie = args[1];
            result += ", " + movie;
        }


        return result;
    }
}
