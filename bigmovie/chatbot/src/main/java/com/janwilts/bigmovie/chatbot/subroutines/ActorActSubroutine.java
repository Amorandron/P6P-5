package com.janwilts.bigmovie.chatbot.subroutines;

import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

/**
 * @author Everdien
 */
public class ActorActSubroutine implements Subroutine {

    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "...";

        String type = args[0];
        String quantum = args[1];
        String rating = args[2];
        if(type.contains("actor") && type.contains("actress")){
            type = "both";
        }else if(type.contains("actor")){
            type = "male";
        }else if(type.contains("actress")){
            type = "female";
        }else{
            return "error";
        }

        //has to be done in more subroutines --> method??
        if(quantum.contains("most")){
            quantum = "most";
        }else if(quantum.contains("least")){
            quantum = "least";
        }

        if(rating.contains("worst")){
            rating = "worst";
        }else if(rating.contains("best")){
            rating = "best";
        }

        result = type + ", " + quantum + ", " + rating;

        return result;
    }
}
