package com.ykapps.bigmovie.models;

import com.github.davidmoten.rx.jdbc.Database;
import rx.Observable;

import java.util.List;

public class Model {

    public enum DbClasses{
        MOVIE,
        ACTOR,
        GENRE,
        SOUNDTRACK,
        GROSS,
        COUNTRY,
    }

    public static final String SQL_SELECT_MOVIE = "SELECT * FROM movie WHERE movie_id=9";

    private Database db;

    public Model(Database db) {
        this.db = db;
    }

    public Observable<String> query(String sql) {
        return db.select(sql)
                .getAs(String.class);
    }

    public Observable queryParameter(DbClasses dbClass, String sql, Object[] para) {
        query(dbClass, sql);

        return db.select(sql)
                .parameter(para)
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
        if(mappingClass.equals(Gross.class)) {
            return db
                    .select(sql)
                    .get(rs -> new Gross(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                            rs.getBigDecimal(4), rs.getDate(5)));
        }
        else if(mappingClass.equals(Movie.class)) {
            return db
                    .select(sql)
                    .get(rs -> new Movie(rs.getInt(1), rs.getString(2), rs.getInt(3),
                            rs.getString(4), rs.getInt(5), rs.getString(6),
                            rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getBigDecimal(10)));
        }

        else {
            return db
                    .select(sql)
                    .autoMap(mappingClass);
        }
    }

}
