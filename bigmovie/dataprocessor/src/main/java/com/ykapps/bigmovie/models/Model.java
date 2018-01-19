package com.ykapps.bigmovie.models;

import com.github.davidmoten.rx.jdbc.Database;
import rx.Observable;

import java.util.HashMap;
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
    public static final String SQL_A7 = "SELECT * FROM movie WHERE budget IS NOT NULL ORDER BY budget DESC LIMIT 5";
    public static final String SQL_A8_EVER = "SELECT cur.* FROM gross cur WHERE NOT EXISTS (SELECT * FROM gross high WHERE high.movie_id = cur.movie_id AND high.country_id = cur.country_id AND high.amount > cur.amount) AND cur.amount IS NOT NULL AND cur.country_id = (select country_id from country where country = ? limit 1) ORDER BY cur.amount DESC LIMIT 20";
    public static final String SQL_A8_DAYS_P1 = "SELECT * FROM gross WHERE gross_id IN (SELECT gross_id FROM gross_by_period WHERE country_id = (SELECT country_id FROM country WHERE country = ? LIMIT 1) AND transaction_date_delta = ";
    public static final String SQL_A8_DAYS_P2 = " ) ORDER BY amount DESC LIMIT 20";
    public static final String SQL_A15 = "SELECT * FROM actor WHERE actor_id=(SELECT actor_id FROM actor_rating WHERE rating=1 GROUP BY actor_id ORDER BY COUNT(actor_id) DESC LIMIT 1)";
    public static final String SQL_A21_SOUNDTRACK = "SELECT * FROM soundtrack WHERE song=(SELECT song FROM soundtrack GROUP BY song ORDER BY COUNT(song) DESC LIMIT 1) LIMIT 1";
    public static final String SQL_A21_MOVIE = "SELECT movie.* FROM most_used_song_ids INNER JOIN movie ON most_used_song_ids.movie_id=movie.movie_id";
    public static final String SQL_B5 = "SELECT x.release_year, y.genre FROM ( SELECT cur.* FROM movie_genre_year AS cur WHERE NOT EXISTS( SELECT high.* FROM movie_genre_year high WHERE cur.release_year = high.release_year AND high.movie_count > cur.movie_count ) ORDER BY release_year ASC ) AS x LEFT JOIN public.genre AS y ON x.genre_id = y.genre_id";
    public static final String SQL_D1 = "SELECT * FROM country WHERE country_id IN ( SELECT country_id FROM public.gross GROUP BY country_id HAVING sum(amount) IS NOT NULL ORDER BY sum(amount) DESC )";

    private Database db;

    public Model(Database db) {
        this.db = db;
    }

    public Observable query(DbClasses dbClass, String sql) {

        Class mappingClass = getMappingClass(dbClass);

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

    public Observable query(DbClasses dbClass, String sql, Object[] params) {

        Class mappingClass = getMappingClass(dbClass);

        //noinspection unchecked
        if(mappingClass.equals(Gross.class)) {
            return db
                    .select(sql)
                    .parameters(params)
                    .get(rs -> new Gross(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                            rs.getBigDecimal(4), rs.getDate(5)));
        }
        else if(mappingClass.equals(Movie.class)) {
            return db
                    .select(sql)
                    .parameters(params)
                    .get(rs -> new Movie(rs.getInt(1), rs.getString(2), rs.getInt(3),
                            rs.getString(4), rs.getInt(5), rs.getString(6),
                            rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getBigDecimal(10)));
        }
        else {
            return db
                    .select(sql)
                    .parameters(params)
                    .autoMap(mappingClass);
        }
    }

    private Class getMappingClass(DbClasses dbClass) {
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

        return mappingClass;
    }

}
