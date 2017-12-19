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

    protected File file;

    public Parser(File file) {
        this.file = file;
    }

    public abstract void parse();
}
