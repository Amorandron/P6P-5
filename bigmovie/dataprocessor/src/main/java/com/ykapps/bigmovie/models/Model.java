package com.ykapps.bigmovie.models;

import com.github.davidmoten.rx.jdbc.Database;
import rx.Observable;

public class Model {

    public enum DbClasses{
        MOVIE,
        ACTOR,
        GENRE,
        SOUNDTRACK,
        GROSS,
        COUNTRY,
    }
    public static final String SQL_SELECT_MOVIE = "SELECT * FROM movie WHERE movie_id=?";
    public static final String SQL_A7_MOST = "SELECT * FROM movie WHERE budget IS NOT NULL ORDER BY budget DESC LIMIT 5";
    public static final String SQL_A7_LEAST = "SELECT * FROM movie WHERE budget IS NOT NULL ORDER BY budget ASC LIMIT 5";
    public static final String SQL_A8_EVER = "SELECT cur.* FROM gross cur WHERE NOT EXISTS (SELECT * FROM gross high WHERE high.movie_id = cur.movie_id AND high.country_id = cur.country_id AND high.amount > cur.amount) AND cur.amount IS NOT NULL AND cur.country_id = (SELECT country_id FROM country WHERE LOWER(country) = ? LIMIT 1) ORDER BY cur.amount DESC LIMIT 20";
    public static final String SQL_A8_DAYS_P1 = "SELECT * FROM gross WHERE gross_id IN (SELECT gross_id FROM gross_by_period WHERE country_id = (SELECT country_id FROM country WHERE LOWER(country) = ? LIMIT 1) AND transaction_date_delta = ";
    public static final String SQL_A8_DAYS_P2 = " ) ORDER BY amount DESC LIMIT 20";
    public static final String SQL_A15_MOST = "SELECT * FROM actor WHERE actor_id=(SELECT ar.actor_id FROM actor_rating AS ar, actor AS a WHERE rating=? AND ar.actor_id = a.actor_id AND a.gender LIKE ? GROUP BY ar.actor_id ORDER BY COUNT(ar.actor_id) DESC LIMIT 1)";
    public static final String SQL_A15_LEAST = "SELECT * FROM actor WHERE actor_id=(SELECT ar.actor_id FROM actor_rating AS ar, actor AS a WHERE rating=? AND ar.actor_id = a.actor_id AND a.gender LIKE ? GROUP BY ar.actor_id ORDER BY COUNT(ar.actor_id) ASC LIMIT 1)";
    public static final String SQL_A21_SOUNDTRACK = "SELECT * FROM soundtrack WHERE song=(SELECT song FROM soundtrack GROUP BY song ORDER BY COUNT(song) DESC LIMIT 1) LIMIT 1";
    public static final String SQL_A21_MOVIE = "SELECT * FROM movie WHERE movie.movie_id IN (SELECT DISTINCT movie_id FROM most_used_song_ids)";
    public static final String SQL_B5 = "SELECT x.release_year, y.genre FROM ( SELECT cur.* FROM movie_genre_year AS cur WHERE NOT EXISTS( SELECT high.* FROM movie_genre_year high WHERE cur.release_year = high.release_year AND high.movie_count > cur.movie_count ) ORDER BY release_year ASC ) AS x LEFT JOIN public.genre AS y ON x.genre_id = y.genre_id";
    public static final String SQL_D1_MOST = "SELECT h.country_id, country FROM (SELECT country_id, SUM(amount) AS amount FROM public.gross cur WHERE NOT EXISTS (SELECT * FROM public.gross high WHERE high.movie_id = cur.movie_id AND high.country_id = cur.country_id AND high.amount > cur.amount) AND cur.amount IS NOT NULL AND NOT cur.country_id = 245 AND NOT cur.country_id = 247 GROUP BY cur.country_id ORDER BY amount DESC LIMIT 10) h, country c WHERE h.country_id = c.country_id";
    public static final String SQL_D1_LEAST = "SELECT h.country_id, country FROM (SELECT country_id, SUM(amount) AS amount FROM public.gross cur WHERE NOT EXISTS (SELECT * FROM public.gross high WHERE high.movie_id = cur.movie_id AND high.country_id = cur.country_id AND high.amount > cur.amount) AND cur.amount IS NOT NULL AND NOT cur.country_id = 245 AND NOT cur.country_id = 247 GROUP BY cur.country_id ORDER BY amount ASC LIMIT 10) h, country c WHERE h.country_id = c.country_id";
    public static final String SQL_D1_MOST_DAYS = "SELECT * FROM gross WHERE gross_id IN (SELECT gross_id FROM gross_by_period WHERE transaction_date_delta = ?) ORDER BY amount DESC LIMIT 10";
    public static final String SQL_D1_LEAST_DAYS = "SELECT * FROM gross WHERE gross_id IN (SELECT gross_id FROM gross_by_period WHERE transaction_date_delta = ?) ORDER BY amount ASC LIMIT 10";
    public static final String SQL_D2 = "SELECT * FROM public.movie WHERE movie_id IN (SELECT movie_id FROM public.movie_actor AS ma, public.actor AS a WHERE ma.actor_id = a.actor_id AND LOWER(a.name) = ?) ORDER BY release_year";
    public static final String SQL_D2_REVERSE = "SELECT * FROM public.actor WHERE actor_id IN( SELECT ma.actor_id FROM public.movie_actor AS ma, public.actor AS a, public.movie AS mo WHERE ma.actor_id = a.actor_id AND ma.movie_id = mo.movie_id AND LOWER(mo.title) = ? ) ORDER BY name";
    public static final String SQL_B4 = "SELECT * " +
            "FROM public.country " +
            "WHERE lower(country) LIKE ?";
    public static final String SQL_Search_Movie = "SELECT * " +
            "FROM public.movie " +
            "WHERE LOWER(title) LIKE ?" +
            "ORDER BY rating_votes DESC NULLS LAST, rating DESC NULLS LAST";

    public static final String SQL_Search_Actor = "SELECT * " +
            "FROM public.actor AS a " +
            "WHERE LOWER(name) LIKE ? " +
            "ORDER BY name ASC";

    public static final String SQL_Movies_by_Country = "SELECT * FROM movie WHERE movie_id IN (" +
            "SELECT DISTINCT movie_id FROM movie_country WHERE country_id=(" +
                "SELECT country_id FROM country WHERE LOWER(country) = ?))";

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
