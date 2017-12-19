package com.janwilts.bigmovie.parser.parsers;

import java.io.File;

public abstract class Parser {
    private static Parser currentParser;

    public static void parseFile(File file) {
        // TODO Implement parser selection logic here
        currentParser = new MovieParser(file);
        currentParser.parse();
    }

    private File file;

    public Parser(File file) {
        this.file = file;
    }

    public abstract void parse();
}
