package com.janwilts.bigmovie.parser.commands;

import com.janwilts.bigmovie.parser.Main;
import com.janwilts.bigmovie.parser.inserters.Inserter;

public class InsertionCommand implements Command{
    // Inserts all parsed files into the database. Can only be used in conjunction with the full parse

    @Override
    public void execute() {
        Main.connection.open();
        Inserter.insertFiles(Main.connection);
    }
}
