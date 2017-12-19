package com.janwilts.bigmovie.parser.commands;

public class FullCommand implements Command {
    private String directory;

    FullCommand(String directory) {
        this.directory = directory;
    }

    @Override
    public void execute() {
        // TODO: Implement command logic
    }
}
