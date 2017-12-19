package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.enums.RequiredFile;

import java.io.File;

public class MovieParser extends Parser {

    public MovieParser(File file) {
        super(file);
        this.requiredFiles.add(RequiredFile.MOVIES);
    }

    @Override
    public void parse() {

    }

    @Override
    public void parse() {

    }
}
