package com.janwilts.bigmovie.parser.parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Parser {
    private static Parser currentParser;

    protected File file;

    public static void parseFile(File file) {
        // TODO: Add corresponding parsers
        if(checkFileName(file, 0))
            currentParser = new MovieParser(file);
        else if(checkFileName(file, 1))
            throw new UnsupportedOperationException();
        else if(checkFileName(file, 2))
            throw new UnsupportedOperationException();
        else if(checkFileName(file, 3))
            throw new UnsupportedOperationException();
        else if(checkFileName(file, 4))
            throw new UnsupportedOperationException();
        else if(checkFileName(file, 5))
            throw new UnsupportedOperationException();
        else if(checkFileName(file, 6))
            throw new UnsupportedOperationException();
        else if(checkFileName(file, 7))
            throw new UnsupportedOperationException();
        else if(checkFileName(file, 8))
            throw new UnsupportedOperationException();
        else if(checkFileName(file, 9))
            throw new UnsupportedOperationException();

        currentParser.parse();
    }

    private static Boolean checkFileName(File file, int index) {
        //return file.getName().equals(RequiredFile.getList().get(index));
    }

    public Parser(File file) {
        this.file = file;
        //this.requiredFiles = new ArrayList<>();
    }

    public abstract void parse();
}
