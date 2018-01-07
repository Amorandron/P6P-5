package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.Parsable;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;
import java.util.List;

public abstract class Inserter {
    public void insertFiles(List<Parsable> parsedFiles) {

    }

    abstract void insert(File file, DatabaseConnection connection);
    abstract String[] getRequiredTables();
}
