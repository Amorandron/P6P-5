package com.janwilts.bigmovie.parser.commands;

import com.janwilts.bigmovie.parser.parsers.Parser;

import java.io.File;

/**
 * @author Jan
 */
public class SingleCommand implements Command {
    private String file;

    // Only parses a single file to csv

    SingleCommand(String file) {
        this.file = file;
    }

    @Override
    public void execute() {
        Parser.parseFile(new File(file));
    }
}
