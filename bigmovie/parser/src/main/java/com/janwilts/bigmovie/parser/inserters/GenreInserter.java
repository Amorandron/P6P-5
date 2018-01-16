package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.parsers.Parser;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;
import java.io.IOException;

/**
 * @author Jan
 */
public class GenreInserter extends Inserter {
    public GenreInserter(File file, DatabaseConnection connection) {
        super(file, connection);
    }

    @Override
    public void insert() {
        message();
        try {
            executeSQL("genre.sql");
            executeInsert("insertion.genre", csv.getCanonicalPath(), Parser.DELIMITER);
            executeSQL("genrejoiner.sql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getRequiredTables() {
        return new String[] {
                "genre",
                "movie_genre",
                "movie"
        };
    }
}
