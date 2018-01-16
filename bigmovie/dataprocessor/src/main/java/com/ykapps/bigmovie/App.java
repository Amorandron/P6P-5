package com.ykapps.bigmovie;

import com.github.davidmoten.rx.jdbc.Database;
import com.ykapps.bigmovie.models.*;
import com.ykapps.bigmovie.util.RRunner;
import org.jooby.Jooby;
import org.jooby.Results;
import org.jooby.apitool.ApiTool;
import org.jooby.banner.Banner;
import org.jooby.jdbc.Jdbc;
import org.jooby.json.Jackson;
import org.jooby.rx.Rx;
import org.jooby.rx.RxJdbc;
import org.rosuda.JRI.Rengine;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yannick Kooistra
 */
@SuppressWarnings("unchecked")
public class App extends Jooby {

    private Model model;

    private RRunner runner;

    {
        use(new Rx());
        use(new Jdbc("db"));
        use(new RxJdbc());

        use(new Jackson());

        use(new ApiTool()
            .raml("/api")
            .filter(route -> !route.pattern().equals("/")));

        use(new Banner("Data Processor"));

        onStart(() -> {
            model = new Model(require(Database.class));

            runner = new RRunner();
        });


        get("/", () -> {
           return Results.redirect("/api");
        });

        get("/movies", () -> {
            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<Movie> obs = model.query(Model.DbClasses.MOVIE, Model.SQL_SELECT_MOVIE);
            Movie movie = obs.toBlocking().first();

            return movie;
        });

        get("/actors", () -> {
            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<Actor> obs = model.query(Model.DbClasses.ACTOR, "SELECT * " +
                    "FROM public.actor");
            Actor actor = obs.toBlocking().first();

            return actor;
        });

        get("/countries", () -> {
            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<Country> obs = model.query(Model.DbClasses.COUNTRY, "SELECT * " +
                    "FROM public.country");
            Country country = obs.toBlocking().first();

            return country;
        });

        get("/genres", () -> {
            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<Genre> obs = model.query(Model.DbClasses.GENRE, "SELECT * " +
                    "FROM public.genre");
            Genre genre = obs.toBlocking().first();

            return genre;
        });

        get("/gross", () -> {
            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<Gross> obs = model.query(Model.DbClasses.GROSS, "SELECT * " +
                    "FROM public.gross WHERE gross_id=140");
            Gross gross = obs.toBlocking().first();

            return gross;
        });

        get("/soundtracks", () -> {
            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<Soundtrack> obs = model.query(Model.DbClasses.SOUNDTRACK, "SELECT * " +
                    "FROM public.soundtrack");
            Soundtrack soundtrack = obs.toBlocking().first();

            return soundtrack;
        });

        get("/q/a7", () -> {
            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<Movie> obs = model.query(Model.DbClasses.MOVIE,"SELECT * " +
                    "FROM public.movie " +
                    "ORDER BY budget DESC");
            Movie movie = obs.toBlocking().first();

            return movie;
        });

        get("/q/a8", () -> {
            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<String> result = model.query("SELECT *");

            String string = result.toBlocking().first();

            return string;
        });

        get("/q/a21", () -> {
            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<String> song = model.query("SELECT song" +
                    "FROM public.soundtrack" +
                    "GROUP BY song" +
                    "ORDER BY count(soundtrack_id) DESC" +
                    "LIMIT 1");
            Observable<Movie> obsM = model.query(Model.DbClasses.MOVIE,"SELECT *" +
                    "FROM public.movie" +
                    "WHERE movie_id IN (" +
                    "  SELECT movie_id" +
                    "  FROM public.soundtrack" +
                    "  WHERE song IN (" +
                    "    SELECT song" +
                    "    FROM public.soundtrack" +
                    "    GROUP BY song" +
                    "    ORDER BY count(soundtrack_id) DESC" +
                    "    LIMIT 1" +
                    "  )" +
                    ")");

            Map<String, Movie> movies = new HashMap<>();

            obsM.forEach(m -> movies.put(song.toBlocking().first(), m));
            return movies;
        });
    }

  public static void main(final String[] args) {
    run(App::new, args);
  }
}
