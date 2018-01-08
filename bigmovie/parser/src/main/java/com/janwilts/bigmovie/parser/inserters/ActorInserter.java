package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;

public class ActorInserter extends Inserter {
    public ActorInserter(File file, DatabaseConnection connection) {
        super(file, connection);
    }

    @Override
    void insert() {

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

