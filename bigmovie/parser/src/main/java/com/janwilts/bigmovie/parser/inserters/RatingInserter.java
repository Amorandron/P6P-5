package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;

public class RatingInserter extends Inserter {
    public RatingInserter(File file, DatabaseConnection connection) {
        super(file, connection);
    }

    @Override
    public void insert() {

    }

    @Override
    public String[] getRequiredTables() {
        return new String[] {
                "movie"
        };
    }
}
