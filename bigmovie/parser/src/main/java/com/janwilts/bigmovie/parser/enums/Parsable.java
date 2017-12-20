package com.janwilts.bigmovie.parser.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Parsable {
    MOVIES("movies"),
    ACTORS("actors"),
    ACTRESSES("actresses"),
    BIOGRAPHIES("biographies"),
    BUSINESS("business"),
    RATINGS("ratings"),
    SOUNDTRACKS("soundtracks"),
    COUNTRIES("countries"),
    GENRES("genres"),
    MPAA_RATINGS_REASONS("mpaa-ratings-reasons");

    private String name;

    Parsable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<String> getList() {
        return Arrays.stream(Parsable.values())
                .map(Parsable::toString)
                .collect(Collectors.toList());
    }
}

