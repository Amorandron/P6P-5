package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;

public class MpaaInserter extends Inserter {

    public MpaaInserter(File file, DatabaseConnection connection) {
        super(file, connection);
    }

    @Override
    void insert() {

    }

    @Override
    public String[] getRequiredTables() {
        return new String[] {
                "movie"
        };
    }
}
