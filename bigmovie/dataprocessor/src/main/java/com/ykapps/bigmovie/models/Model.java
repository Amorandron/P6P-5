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
    public static final String SQL_B5 = "SELECT x.release_year, y.genre FROM ( SELECT cur.* FROM movie_genre_year AS cur WHERE NOT EXISTS( SELECT high.* FROM movie_genre_year high WHERE cur.release_year = high.release_year AND high.movie_count > cur.movie_count ) ORDER BY release_year ASC ) AS x LEFT JOIN public.genre AS y ON x.genre_id = y.genre_id";
    public static final String SQL_D1 = "SELECT * FROM country WHERE country_id IN ( SELECT country_id FROM public.gross GROUP BY country_id HAVING sum(amount) IS NOT NULL ORDER BY sum(amount) DESC )";
    public static final String SQL_Search_Movie = "SELECT b.title, b.release_year, b.occurence, b.type, b.budget, b.mpaa_rating, b.rating, b.rating_votes, c.country, g.genre FROM ( SELECT * FROM ( SELECT x.*, y.country_id, z.genre_id FROM public.movie AS x LEFT JOIN public.movie_country as y ON x.movie_id = y.movie_id LEFT JOIN public.movie_genre AS z ON x.movie_id = z.movie_id AND z.movie_id = y.movie_id ) AS a WHERE LOWER( title ) LIKE '%?%' ORDER BY title ASC LIMIT 30 ) AS b LEFT JOIN public.country AS c ON b.country_id = c.country_id LEFT JOIN public.genre AS g ON b.genre_id = g.genre_id";
    public static final String SQL_Search_Actor = "SELECT * FROM public.actor AS a WHERE name LIKE '%?%' ORDER BY name ASC";
    public static final String SQL_Movies_by_Country = "SELECT a.count, b.country FROM ( SELECT COUNT(movie_id), country_id FROM public.movie_country GROUP BY country_id ORDER BY count DESC ) AS a LEFT JOIN public.country AS b ON b.country_id = a.country_id WHERE country = '%?%'";

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
