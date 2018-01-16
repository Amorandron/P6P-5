package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActorRoleSubroutine implements Subroutine {
    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "";

        //TODO get request from api.

        if (args.length == 0) {
            return result;
        }

        if (args.length == 3) {
            result += args[0] + " " + args[1] + ", " + args[2];
        }
        else if (args.length < 3) {
            result += args[0] + ", " + args[1];
        }
        else {
            String firstName = args[0];
            String lastName = "";
            for (int i = 1; i<args.length - 1; i++) {
                lastName += args[i];
                lastName += " ";
            }
            String type = args[args.length - 1];

            result = firstName + " " + lastName + " " + type;
        }

        return result;
    }
}
