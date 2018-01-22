package com.janwilts.bigmovie.parser;

import com.github.shyiko.dotenv.DotEnv;
import com.janwilts.bigmovie.parser.commands.CommandParser;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.util.Map;

/**
 * @author Jan
 */
public class Main {
    public static final String outputDirectory = "output/";
    public static final Map<String, String> dotEnv = DotEnv.load();
    public static final DatabaseConnection connection = new DatabaseConnection(dotEnv.get("DATABASE_HOST"),
            Integer.valueOf(dotEnv.get("DATABASE_PORT")),
            dotEnv.get("DATABASE_NAME"),
            dotEnv.get("DATABASE_USERNAME"),
            dotEnv.get("DATABASE_PASSWORD"));

    public static void main(String[] args) {
        CommandParser.parse(args);
    }
}
