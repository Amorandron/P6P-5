package com.janwilts.bigmovie.parser.inserters;

import com.janwilts.bigmovie.parser.Main;
import com.janwilts.bigmovie.parser.Parsable;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Jan
 */
// Base class for inserting files into the database, also used for static purposes
public abstract class Inserter {
    private static final String[] order = new String[] {"movies", "countries", "genres", "soundtracks", "actors"};

    protected DatabaseConnection connection;
    protected File csv;

    // Main static insertion command loops through all the inserts in array in right order and executes them
    public static void insertFiles(DatabaseConnection connection) {
        try {
            for(String task : order)
                getInserter(task, connection).insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gets the right inserter class from string
    public static Inserter getInserter(String csv, DatabaseConnection connection) throws Exception {
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
        this.csv = file;
        this.connection = connection;
    }

    // Executes an SQL file located in resources/scripts/
    protected void executeSQL(String file) throws IOException {
        URL url = this.getClass().getResource("/scripts/" + file);
        File sqlFile = null;
        try {
            sqlFile = new File(Objects.requireNonNull(url).toURI());
        } catch (URISyntaxException e) {
            sqlFile = new File(Objects.requireNonNull(url).getPath());
        }
        String sql = FileUtils.readFileToString(sqlFile, "UTF-8");
        sql = sql.replaceAll("[\n]", " ");
        sql = sql.replaceAll("[ ]{2,}", " ");

        List<String> queries = Arrays.stream(sql.split(";"))
                .map(String::trim)
                .collect(Collectors.toList());

        for(String query : queries) {
            try {
                connection.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Copy's CSV file onto the database, we use this and not SQL because SQL is onnly callable if the CSV is already
    // on the server
    protected void executeInsert(String table, String csv, String delimiter) throws Exception {
        connection.getManager().copyIn(String.format("COPY %s FROM STDIN (FORMAT csv, DELIMITER '%s')", table, delimiter),
                new FileReader(new File(csv)));
    }

    protected void message() {
        System.out.println(String.format("Inserting %s...", csv.getName()));
    }

    // Abstract method which is being called by the static method
    abstract void insert();
    public abstract String[] getRequiredTables();
}
