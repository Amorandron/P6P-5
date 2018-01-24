package com.janwilts.bigmovie.chatbot.subroutines;

import com.janwilts.bigmovie.chatbot.discord.DiscordBot;
import com.janwilts.bigmovie.chatbot.models.Actor;
import com.janwilts.bigmovie.chatbot.models.Country;
import com.janwilts.bigmovie.chatbot.models.Movie;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Routine implements Subroutine {
    protected static HashMap<Integer, Movie> focusedMovies = new HashMap<>();
    protected static HashMap<Integer, Actor> focusedActors = new HashMap<>();
    protected static HashMap<Integer, Country> focusedCountries = new HashMap<>();
    protected static Map<Object, Object> mainFocus = new HashMap<>();

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
        mainFocus = map.entrySet()
                .stream()
                .collect(Collectors.toMap(i -> i, v -> v));
    }

    @Override
    public String call(RiveScript rs, String[] args) {
        mainFocus = new HashMap<>();
        return "";
    }
}
