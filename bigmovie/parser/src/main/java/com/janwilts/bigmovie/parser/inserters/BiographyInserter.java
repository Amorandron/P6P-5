package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.parsers.Parser;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;

/**
 * @author Jan
 */
public class BiographyInserter extends Inserter {
    public BiographyInserter(File file, DatabaseConnection connection) {
        super(file, connection);
    }

    @Override
    public void insert() {
        message();
        try {
            executeSQL("biography.sql");
            executeInsert("insertion.biography", csv.getCanonicalPath(), Parser.DELIMITER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getRequiredTables() {
        return new String[] {
                "actor"
        };
    }
}
