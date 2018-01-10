package com.janwilts.bigmovie.chatbot.subroutines;

import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

public class MovieCostSubroutine implements Subroutine {

    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "...";

        //TODO get data from database and process it.

        String type = args[0];
        if(type.contains("most")){
            result = "most";
        }else if(type.contains("least") || type.equals("cheapest")){
            result = "least";
        }

        return result;
    }
}
