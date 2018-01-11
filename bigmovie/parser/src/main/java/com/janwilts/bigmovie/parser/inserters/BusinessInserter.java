package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.parsers.Parser;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;

public class BusinessInserter extends Inserter {
    public BusinessInserter(File file, DatabaseConnection connection) {
        super(file, connection);
    }

    @Override
    public void insert() {
        try {
            executeSQL("business.sql");
            executeInsert("insertion.business", csv.getCanonicalPath(), Parser.DELIMITER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getRequiredTables() {
        return new String[] {
                "movie",
                "gross",
                "country"
        };
    }
}
