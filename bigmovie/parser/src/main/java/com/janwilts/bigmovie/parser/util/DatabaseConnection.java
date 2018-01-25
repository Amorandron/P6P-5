package com.janwilts.bigmovie.parser.util;

import com.google.common.base.Strings;
import com.janwilts.bigmovie.parser.Parsable;
import org.postgresql.copy.CopyManager;
import org.postgresql.jdbc.PgConnection;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jan
 */
public class DatabaseConnection {
    private String host;
    private Integer port;
    private String database;
    private String username;
    private String password;

    private Boolean status;

    private PgConnection connection;
    private CopyManager manager;

    public DatabaseConnection(String host, Integer port, String database, String username, String password) {
        if(Strings.isNullOrEmpty(host) ||
            port == 0 ||
            Strings.isNullOrEmpty(database) ||
            Strings.isNullOrEmpty(username) ||
            Strings.isNullOrEmpty(password))
            throw new InputMismatchException("Please make sure your connection data is set inside your .env file");
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    // Opens connection and tests if all the required tables are present
    public Boolean open() {
        Boolean result;
        try {
            connection = (PgConnection) DriverManager.getConnection(String.format("jdbc:postgresql://%s:%d/%s",
                    host, port, database), username, password);

            result = testTables(connection.createStatement());
            manager = new CopyManager(connection);
        } catch (SQLException e) {
            result = false;
        }
        status = result;

        return result;
    }

    // Closes connection
    public void close() throws SQLException {
        connection.close();
    }

    // Method to test if all required tables are present which it gets from the different inserter classes.
    private Boolean testTables(Statement testStatement) throws SQLException {
        List<String> output = new ArrayList<>();
        ResultSet testSet = testStatement.executeQuery("SELECT * FROM pg_catalog.pg_tables");
        while(testSet.next())
            output.add(testSet.getString(2));

        testStatement.close();


        List<String> requiredTables = new ArrayList<>();
        for(Parsable p : Parsable.getList()) {
            try {
                requiredTables.addAll(Arrays.asList(p.getInserter(this).getRequiredTables()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return output.containsAll(requiredTables.stream()
                .distinct()
                .collect(Collectors.toList()));
    }

    // Executes a string of SQl
    public void execute(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
    }

    // Gets the copy manager for copying csv onto the server
    public CopyManager getManager() {
        return manager;
    }
}
