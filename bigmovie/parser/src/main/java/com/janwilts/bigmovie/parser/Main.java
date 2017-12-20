package com.janwilts.bigmovie.parser;

import com.github.shyiko.dotenv.DotEnv;
import com.janwilts.bigmovie.parser.commands.CommandParser;
import com.janwilts.bigmovie.parser.enums.RequiredFile;
import com.janwilts.bigmovie.parser.parsers.Parser;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static final Map<String, String> dotEnv = DotEnv.load();

    public static void main(String[] args) {
        CommandParser.parse(args);
    }
}
