package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Actor;
import com.janwilts.bigmovie.chatbot.models.Country;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Routine implements Subroutine {
    protected static LinkedHashMap<Integer, Movie> focusedMovies = new LinkedHashMap<>();
    protected static LinkedHashMap<Integer, Actor> focusedActors = new LinkedHashMap<>();
    protected static LinkedHashMap<Integer, Country> focusedCountries = new LinkedHashMap<>();
    protected static Map<Integer, ?> mainFocus = new LinkedHashMap<>();

    protected DiscordBot bot;

    public Routine(DiscordBot bot) {
        this.bot = bot;
    }

    protected String fileOutput(File file) {
        return String.format("{{file}}%s", file.getPath());
    }

    protected String formatOutput(String... param) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < param.length; i++) {
            builder.append(param[i]);
            if(i != param.length - 1)
                builder.append("||");
        }

        return builder.toString();
    }

    protected void setMainFocus(Map<Integer, ?> map) {
        mainFocus = map;
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        mainFocus = new HashMap<>();
        return "";
    }
}
