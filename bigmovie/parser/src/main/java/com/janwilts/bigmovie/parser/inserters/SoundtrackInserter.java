package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.parsers.Parser;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;

/**
 * @author Jan
 */
public class SoundtrackInserter extends Inserter {
    public SoundtrackInserter(File file, DatabaseConnection connection) {
        super(file, connection);
    }

    @Override
    public void insert() {
        message();
        try {
            executeSQL("soundtrack.sql");
            executeInsert("insertion.soundtrack", csv.getCanonicalPath(), Parser.DELIMITER);
            executeSQL("soundtrackjoiner.sql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getRequiredTables() {
        return new String[] {
                "soundtrack",
                "movie"
        };
    }
}
