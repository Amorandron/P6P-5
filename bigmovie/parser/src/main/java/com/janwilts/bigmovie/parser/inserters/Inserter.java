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
public abstract class Inserter {
    private static final String[] order = new String[] {"movies", "countries", "genres", "soundtracks", "actors"};
    //private static final String[] order = new String[] {"movies", "actors"};

    protected DatabaseConnection connection;
    protected File csv;

    public static void insertFiles(DatabaseConnection connection) {
        try {
            for(String task : order)
                getInserter(task, connection).insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    protected void executeInsert(String table, String csv, String delimiter) throws Exception {
        connection.getManager().copyIn(String.format("COPY %s FROM STDIN (FORMAT csv, DELIMITER '%s')", table, delimiter),
                new FileReader(new File(csv)));
    }

    protected void message() {
        System.out.println(String.format("Inserting %s...", csv.getName()));
    }

    abstract void insert();
    public abstract String[] getRequiredTables();
}
