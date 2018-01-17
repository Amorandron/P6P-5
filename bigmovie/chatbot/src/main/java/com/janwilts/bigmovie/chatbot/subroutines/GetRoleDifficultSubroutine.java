package com.janwilts.bigmovie.chatbot.subroutines;

import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

/**
 * @author Everdien
 */
public class GetRoleDifficultSubroutine implements Subroutine {

    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "...";

        //TODO get request from api.

        String type = args[0];
        String difficulty = args[1];
        String age = args[2];
        Boolean possible = true;
        if(type.contains("actor") && type.contains("actress")){
            type = "both";
            possible = false;
        }else if(type.contains("actor")){
            type = "male";
            possible = false;
        }else if(type.contains("actress")){
            type = "female";
        }else{
            return "error";
        }

        if(difficulty.contains("most")){
            difficulty = "most";
        }else if(difficulty.contains("least")){
            difficulty = "least";
        }

        if(age.contains("younger")){
            age = "younger";
        }else if(age.contains("older")){
            age = "older";
        }

        if(possible){
            result = type + ", " + difficulty + ", " + age;
        }else{
            result = "I can only answer this question for actresses at the time.";
        }


        return result;
    }
}
