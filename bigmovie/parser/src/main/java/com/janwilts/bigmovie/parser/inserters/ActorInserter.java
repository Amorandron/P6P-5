package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.parsers.Parser;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;

public class ActorInserter extends Inserter {
    public ActorInserter(File file, DatabaseConnection connection) {
        super(file, connection);
    }

    @Override
    void insert() {
        message();
        try {
            Inserter.getInserter("biographies", connection).insert();
            executeSQL("actor.sql");
            executeInsert("insertion.actor", csv.getCanonicalPath(), Parser.DELIMITER);
            File actressFile = new File(csv.getCanonicalPath().substring(0, csv.getCanonicalPath().lastIndexOf('/') + 1) + "actresses.csv");
            Inserter actress = new ActressesInserter(actressFile, connection);
            actress.insert();
            executeSQL("actorjoiner.sql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getRequiredTables() {
        return new String[] {
                "actor",
                "actor_movie",
                "movie"
        };
    }
}

