package com.janwilts.bigmovie.parser.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jan
 */
public enum Parsable {
    MOVIES("movies", "MovieParser"),
    ACTORS("actors", "ActorParser"),
    ACTRESSES("actresses", "ActorParser"),
    BIOGRAPHIES("biographies", "BiographyParser"),
    BUSINESS("business", "BusinessParser"),
    RATINGS("ratings", "RatingParser"),
    SOUNDTRACKS("soundtracks", "SoundtrackParser"),
    COUNTRIES("countries", "CountryParser"),
    GENRES("genres", "GenreParser"),
    MPAA_RATINGS_REASONS("mpaa-ratings-reasons", "MpaaParser");

    private String name;
    private Class c;

    Parsable(String name, String className) {
        this.name = name;
        try {
            this.c = Class.forName("com.janwilts.bigmovie.parser.parsers." + className);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public Class getC() {
        return c;
    }

    public static List<Parsable> getList() {
        return Arrays.stream(Parsable.values())
                .collect(Collectors.toList());
    }
}

