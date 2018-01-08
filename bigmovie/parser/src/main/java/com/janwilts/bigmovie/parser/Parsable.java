package com.janwilts.bigmovie.parser;

import com.janwilts.bigmovie.parser.parsers.Parser;
import com.janwilts.bigmovie.parser.util.DatabaseConnection;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jan
 */
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
    private Method inserter;

    Parsable(String name, String single) {
        this.name = name;
        try {
            this.parser = Class.forName("com.janwilts.bigmovie.parser.parsers." + single + "Parser");
            this.inserter = Class.forName("com.janwilts.bigmovie.parser.inserters." + single + "Inserter")
                    .getMethod("insert", File.class, DatabaseConnection.class);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
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

    public Method getInserter() {
        return inserter;
    }

    public static List<Parsable> getList() {
        return Arrays.stream(Parsable.values())
                .collect(Collectors.toList());
    }
}

