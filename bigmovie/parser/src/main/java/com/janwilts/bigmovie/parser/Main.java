package com.janwilts.bigmovie.parser;

import com.github.shyiko.dotenv.DotEnv;
import com.janwilts.bigmovie.parser.commands.CommandParser;

import java.util.Map;

public class Main {
    public static final Map<String, String> dotEnv = DotEnv.load();

    public static void main(String[] args) {
        CommandParser.parse(args);
    }
}
