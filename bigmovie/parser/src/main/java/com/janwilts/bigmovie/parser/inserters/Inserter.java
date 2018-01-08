package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.Main;
import com.janwilts.bigmovie.parser.Parsable;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class Inserter {
    private static final String[] order = new String[] {"movie", "actor", "country", "business", "genre", "soundtrack"};

    protected BufferedReader reader;
    protected DatabaseConnection connection;

    public static void insertFiles(DatabaseConnection connection) {
        try {
            for(String task : order)
                getInserter(task, connection).insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Inserter getInserter(String csv, DatabaseConnection connection) throws Exception {
        return getInserter(new File(Main.outputDirectory + csv + ".csv"), connection);
    }

    private static Inserter getInserter(File csv, DatabaseConnection connection) throws Exception {
        return Parsable.getList()
                .stream()
                .filter(p -> p.toString().equals(csv.getName().substring(0, csv.getName().indexOf('.'))))
                .findFirst()
                .get()
                .getInserter(connection);
    }

    public Inserter(File file, DatabaseConnection connection) {
        this.connection = connection;
        try {
            this.reader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] nextLine() throws IOException {
        return reader.readLine().split(",");
    }

    abstract void insert();
    public abstract String[] getRequiredTables();
}
