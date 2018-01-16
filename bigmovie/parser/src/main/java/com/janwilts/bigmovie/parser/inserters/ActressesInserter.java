package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.parsers.Parser;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;

/**
 * @author Jan
 */
public class ActressesInserter extends Inserter {
    public ActressesInserter(File file, DatabaseConnection connection) {
        super(file, connection);
    }

    @Override
    void insert() {
        message();
        try {
            executeInsert("insertion.actor", csv.getCanonicalPath(), Parser.DELIMITER);
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
