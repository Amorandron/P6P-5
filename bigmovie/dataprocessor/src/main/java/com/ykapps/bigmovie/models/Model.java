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
    public static final String SQL_A8_EVER = "SELECT cur.* FROM gross cur WHERE NOT EXISTS (SELECT * FROM gross high WHERE high.movie_id = cur.movie_id AND high.country_id = cur.country_id AND high.amount > cur.amount) AND cur.amount IS NOT NULL AND cur.country_id = (select country_id from country where country = ? limit 1) ORDER BY cur.amount DESC LIMIT 20";
    public static final String SQL_A8_DAYS_P1 = "SELECT * FROM gross WHERE gross_id IN (SELECT gross_id FROM gross_by_period WHERE country_id = (SELECT country_id FROM country WHERE country = ? LIMIT 1) AND transaction_date_delta = ";
    public static final String SQL_A8_DAYS_P2 = " ) ORDER BY amount DESC LIMIT 20";
    public static final String SQL_A15_MOST = "SELECT * FROM actor WHERE actor_id=(SELECT ar.actor_id FROM actor_rating AS ar, actor AS a WHERE rating=? AND ar.actor_id = a.actor_id AND a.gender LIKE ? GROUP BY ar.actor_id ORDER BY COUNT(ar.actor_id) DESC LIMIT 1)";
    public static final String SQL_A15_LEAST = "SELECT * FROM actor WHERE actor_id=(SELECT ar.actor_id FROM actor_rating AS ar, actor AS a WHERE rating=? AND ar.actor_id = a.actor_id AND a.gender LIKE ? GROUP BY ar.actor_id ORDER BY COUNT(ar.actor_id) ASC LIMIT 1)";
    public static final String SQL_A21_SOUNDTRACK = "SELECT * FROM soundtrack WHERE song=(SELECT song FROM soundtrack GROUP BY song ORDER BY COUNT(song) DESC LIMIT 1) LIMIT 1";
    public static final String SQL_A21_MOVIE = "SELECT movie.* FROM most_used_song_ids INNER JOIN movie ON most_used_song_ids.movie_id=movie.movie_id";
    public static final String SQL_B5 = "SELECT x.release_year, y.genre FROM ( SELECT cur.* FROM movie_genre_year AS cur WHERE NOT EXISTS( SELECT high.* FROM movie_genre_year high WHERE cur.release_year = high.release_year AND high.movie_count > cur.movie_count ) ORDER BY release_year ASC ) AS x LEFT JOIN public.genre AS y ON x.genre_id = y.genre_id";
    public static final String SQL_D1 = "SELECT * FROM country WHERE country_id IN ( SELECT country_id FROM public.gross GROUP BY country_id HAVING sum(amount) IS NOT NULL ORDER BY sum(amount) DESC )";
    public static final String SQL_B4 = "SELECT * " +
            "FROM public.country " +
            "WHERE lower(country) LIKE ?";
    public static final String SQL_Search_Movie = "SELECT b.*" +
            "FROM (SELECT * " +
                "FROM (SELECT x.*, y.country_id, z.genre_id " +
                    "FROM public.movie AS x " +
                    "LEFT JOIN public.movie_country AS y " +
                    "ON x.movie_id = y.movie_id " +
                    "LEFT JOIN public.movie_genre AS z " +
                    "ON x.movie_id = z.movie_id " +
                    "AND z.movie_id = y.movie_id ) AS a " +
                "WHERE LOWER( title ) LIKE ?" +
                "ORDER BY title ASC LIMIT 30 ) AS b " +
                "LEFT JOIN public.country AS c " +
                "ON b.country_id = c.country_id " +
                "LEFT JOIN public.genre AS g " +
                "ON b.genre_id = g.genre_id";

    public static final String SQL_Search_Actor = "SELECT * " +
            "FROM public.actor AS a " +
            "WHERE name LIKE ? " +
            "ORDER BY name ASC";

    public static final String SQL_Movies_by_Country = "SELECT a.count, b.country " +
            "FROM (SELECT COUNT(movie_id), country_id " +
                "FROM public.movie_country " +
                "GROUP BY country_id " +
                "ORDER BY count DESC ) AS a " +
            "LEFT JOIN public.country AS b " +
            "ON b.country_id = a.country_id " +
            "WHERE country = ?";

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
