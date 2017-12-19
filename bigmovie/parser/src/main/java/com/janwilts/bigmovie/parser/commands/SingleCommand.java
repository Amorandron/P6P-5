package com.janwilts.bigmovie.parser.commands;

public class SingleCommand implements Command {
    private String file;

    SingleCommand(String file) {
        this.file = file;
    }

    @Override
    public void execute() {
        // TODO: Implement command logic
    }
}
