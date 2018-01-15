package com.janwilts.bigmovie.chatbot.subroutines;

import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

public class MovieCountryCountSubroutine implements Subroutine {

    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "...";

        String country = args[0];
        result = country;

        if(args.length > 1){
            if(args[1].equals("time")){
                result += ", displayed in time";
            }
        }

        return result;
    }
}
