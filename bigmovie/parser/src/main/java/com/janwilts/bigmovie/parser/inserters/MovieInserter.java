package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.parsers.Parser;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;

public class MovieInserter extends Inserter{
    public MovieInserter(File file, DatabaseConnection connection) {
        super(file, connection);
    }

    @Override
    public void insert() {
        message();
        try {
            Inserter.getInserter("business", connection).insert();
            Inserter.getInserter("mpaa-ratings-reasons", connection).insert();
            Inserter.getInserter("ratings", connection).insert();
            executeSQL("movie.sql");
            executeInsert("insertion.movie", csv.getCanonicalPath(), Parser.DELIMITER);
            executeSQL("moviejoiner.sql");
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
