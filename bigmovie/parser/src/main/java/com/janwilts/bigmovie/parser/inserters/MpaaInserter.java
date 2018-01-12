package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.parsers.Parser;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;

public class MpaaInserter extends Inserter {

    public MpaaInserter(File file, DatabaseConnection connection) {
        super(file, connection);
    }

    @Override
    void insert() {
        message();
        try {
            executeSQL("mpaa.sql");
            executeInsert("insertion.mpaa", csv.getCanonicalPath(), Parser.DELIMITER);
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
