package com.janwilts.bigmovie.parser;

import com.janwilts.bigmovie.parser.inserters.Inserter;
import com.janwilts.bigmovie.parser.parsers.Parser;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jan
 */

// Main enum which stores the names of the input files and their required classes
public enum Parsable {
    MOVIES("movies", "Movie"),
    ACTORS("actors", "Actor"),
    ACTRESSES("actresses", "Actor"),
    BIOGRAPHIES("biographies", "Biography"),
    BUSINESS("business", "Business"),
    RATINGS("ratings", "Rating"),
    SOUNDTRACKS("soundtracks", "Soundtrack"),
    COUNTRIES("countries", "Country"),
    GENRES("genres", "Genre"),
    MPAA_RATINGS_REASONS("mpaa-ratings-reasons", "Mpaa");

    private String name;
    private Class<?> parser;
    private Class<?> inserter;

    Parsable(String name, String single) {
        this.name = name;
        try {
            this.parser = Class.forName("com.janwilts.bigmovie.parser.parsers." + single + "Parser");
            this.inserter = Class.forName("com.janwilts.bigmovie.parser.inserters." + single + "Inserter");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public Parser getParser(File file) throws Exception {
        return (Parser) parser.getConstructor(File.class).newInstance(file);
    }

    public Inserter getInserter(DatabaseConnection connection) throws Exception {
        return (Inserter) inserter.getConstructor(File.class, DatabaseConnection.class)
                .newInstance(new File(Main.outputDirectory + name + ".csv"), connection);
    }

    // Retruns all enum values in a list
    public static List<Parsable> getList() {
        return Arrays.stream(Parsable.values())
                .collect(Collectors.toList());
    }
}

