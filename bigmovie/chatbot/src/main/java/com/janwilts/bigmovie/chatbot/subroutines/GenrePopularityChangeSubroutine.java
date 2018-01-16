package com.janwilts.bigmovie.chatbot.subroutines;

import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

/**
 * @author Everdien
 */
public class GenrePopularityChangeSubroutine implements Subroutine {

    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "It changes.";

        //TODO get request from api.

        return result;
    }
}
