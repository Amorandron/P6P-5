package com.janwilts.bigmovie.parser.parsers;

import java.io.File;

public abstract class Parser {
    public static final String[] REQUIRED_FILES = {
            "movies",
            "actors",
            "actresses",
            "business",
            "ratings",
            "soundtracks",
            "countries",
            "genres",
            "mpaa-ratings-reasons"
    };

    public Parser(File file) {

    }
}
