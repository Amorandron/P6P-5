package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.util.APIRequester;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieEarnedSubroutine implements Subroutine {
    
    @Override
    public String call(RiveScript rs, String[] args) {
//        String result = "ERROR, NO DATA FOUND";
        String result = APIRequester.getFromAPI("movies");
        
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
        return result;
    }
}
