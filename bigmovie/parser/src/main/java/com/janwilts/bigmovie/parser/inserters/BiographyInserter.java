package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;

public class BiographyInserter extends Inserter {
    public BiographyInserter(File file, DatabaseConnection connection) {
        super(file, connection);
    }

    @Override
    public void insert() {

    }

    @Override
    public String[] getRequiredTables() {
        return new String[] {
                "actor"
        };
    }
}
