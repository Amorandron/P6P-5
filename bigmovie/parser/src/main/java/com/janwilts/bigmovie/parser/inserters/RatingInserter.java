package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.parsers.Parser;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;

public class RatingInserter extends Inserter {
    public RatingInserter(File file, DatabaseConnection connection) {
        super(file, connection);
    }

    @Override
    public void insert() {
        message();
        try {
            executeSQL("ratings.sql");
            executeInsert("insertion.ratings", csv.getCanonicalPath(), Parser.DELIMITER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getRequiredTables() {
        return new String[] {
                "movie"
        };
    }
}
