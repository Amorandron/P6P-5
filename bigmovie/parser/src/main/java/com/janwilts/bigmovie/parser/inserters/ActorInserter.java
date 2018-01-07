package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;

public class ActorInserter extends Inserter {
    @Override
    public void insert(File file, DatabaseConnection connection) {

    }

    @Override
    public String[] getRequiredTables() {
        return new String[] {
                "actors",
                "actors_movies",
                "movies"
        };
    }
}
