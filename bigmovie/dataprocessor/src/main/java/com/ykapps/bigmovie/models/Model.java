package com.ykapps.bigmovie.models;

import com.github.davidmoten.rx.jdbc.Database;
import rx.Observable;

import java.util.List;

public class Model{

    public enum DbClasses{
        MOVIE,
        ACTOR,
        GENRE,
        SOUNDTRACK,
        GROSS,
        COUNTRY,
    }

    public static final String SQL_SELECT_ALL_MOVIES = "SELECT * FROM movie";

    private Database db;

    public Model(Database db) {
        this.db = db;
    }

    public Observable<String> query(String sql) {
        return db.select(sql)
                .getAs(String.class);
    }

    public Observable query(DbClasses dbClass, String sql) {

        Class mappingClass = null;

        switch(dbClass) {
            case MOVIE:
                mappingClass = Movie.class;
                break;
            case ACTOR:
                mappingClass = Actor.class;
                break;
            case GENRE:
                mappingClass = Genre.class;
                break;
            case SOUNDTRACK:
                mappingClass = Soundtrack.class;
                break;
            case GROSS:
                mappingClass = Gross.class;
                break;
            case COUNTRY:
                mappingClass = Country.class;
                break;
        }

        //noinspection unchecked
        return db
            .select(sql)
            .autoMap(mappingClass);
    }

}
