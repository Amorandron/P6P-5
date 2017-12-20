package com.janwilts.bigmovie.parser.commands;

import com.janwilts.bigmovie.parser.enums.Parsable;
import com.janwilts.bigmovie.parser.parsers.Parser;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FullCommand implements Command {
    private String directory;

    FullCommand(String directory) {
        this.directory = directory;
    }

    @Override
    public void execute() {
        File dataDirectory = new File(directory);

        List<File> files = Arrays.asList(Objects.requireNonNull(dataDirectory.listFiles()));

        files.stream()
                .filter(f -> Parsable.getList()
                        .contains(f.getName().substring(0, f.getName().indexOf('.'))))
                .forEach(f -> Parser.parseFile(f));
    }
}
