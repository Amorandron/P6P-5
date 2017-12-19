package com.janwilts.bigmovie.parser.commands;

import java.util.List;

public class MultiplesCommand implements Command {
    private List<String> files;

    MultiplesCommand(List<String> files) {
        this.files = files;
    }

    @Override
    public void execute() {
        // TODO: Implement command logic
    }
}
