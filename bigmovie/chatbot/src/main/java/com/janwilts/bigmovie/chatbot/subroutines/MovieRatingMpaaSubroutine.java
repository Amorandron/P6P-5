package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieRatingMpaaSubroutine implements Subroutine {
    @Override
    public String call(RiveScript rs, String[] args) {
        String result = "";

        //TODO get request from api.

        if (args.length == 0) {
            return result;
        }

        for (String arg : args) {
            result += arg;
            result += " ";
        }

        return result;
    }
}
