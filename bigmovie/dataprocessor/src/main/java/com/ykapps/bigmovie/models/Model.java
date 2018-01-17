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
    public static final String SQL_A7 = "SELECT * FROM movie WHERE budget IS NOT NULL ORDER BY budget DESC LIMIT 5";
    public static final String SQL_A15 = "SELECT * FROM actor WHERE actor_id=(SELECT actor_id FROM actor_rating WHERE rating=1 GROUP BY actor_id ORDER BY COUNT(actor_id) DESC LIMIT 1)";
    public static final String SQL_A21_SOUNDTRACK = "SELECT * FROM soundtrack WHERE song=(SELECT song FROM soundtrack GROUP BY song ORDER BY COUNT(song) DESC LIMIT 1) LIMIT 1";
    public static final String SQL_A21_MOVIE = "SELECT movie.* FROM most_used_song_ids INNER JOIN movie ON most_used_song_ids.movie_id=movie.movie_id";
    public static final String SQL_B5 = "select computed.release_year, a.genre from ( select cur.* from movie_genre_year as cur where not exists ( select high.* from movie_genre_year high where cur.release_year = high.release_year and high.movie_count > cur.movie_count ) order by release_year asc ) as computed left join public.genre as a on computed.genre_id = a.genre_id";
    public static final String SQL_D1 = "SELECT * FROM country WHERE country_id IN ( SELECT country_id FROM public.gross GROUP BY country_id HAVING sum(amount) IS NOT NULL ORDER BY sum(amount) DESC )";

    private Database db;

    public Model(Database db) {
        this.db = db;
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
