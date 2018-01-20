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
import org.rosuda.JRI.REXP;
import rx.Observable;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Yannick, Everdien & Jan
 */
@SuppressWarnings("unchecked")
public class App extends Jooby {

    private Model model;

    private RRunner runner;
    private Jdbc jdbc = new Jdbc("db");

    private List<REXP> c2Output;

    {
        use(new Rx());
        use(jdbc);
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

        get("/q/a7/most", () -> {
            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<Movie> result = model.query(Model.DbClasses.MOVIE, Model.SQL_A7_MOST);

            return result.toList().toBlocking().single();
        });

        get("/q/a7/least", () -> {
            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<Movie> result = model.query(Model.DbClasses.MOVIE, Model.SQL_A7_LEAST);

            return result.toList().toBlocking().single();
        });

        get("/q/a8", (request) -> {
            //noinspection unchecked

            String period = request.param("period").value();
            String country = request.param("country").value();

            Object[] params = {country};

            if (period.equals("ever")) {

                Observable<Gross> resultObs = model.query(Model.DbClasses.GROSS, Model.SQL_A8_EVER, params);

                return resultObs.toList().toBlocking().single();
            }
            else {
                try {
                    Integer.parseInt(period);
                }
                catch (NumberFormatException e) {
                    return "Error";
                }

                Observable<Gross> resultObs =  model.query(Model.DbClasses.GROSS, Model.SQL_A8_DAYS_P1 + period + Model.SQL_A8_DAYS_P2, params);

                return resultObs.toList().toBlocking().single();
            }
        });

        get("/q/a15", () -> {
            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<String> result = model.query(Model.DbClasses.ACTOR, Model.SQL_A15);

            return result.toBlocking().first();
        });

        get("/q/a21", () -> {
            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<Soundtrack>  resultSoundtrack = model.query(Model.DbClasses.SOUNDTRACK, Model.SQL_A21_SOUNDTRACK);
            Observable<Movie> resultMovie = model.query(Model.DbClasses.MOVIE, Model.SQL_A21_MOVIE);

            List<List> result = new ArrayList<>();

            result.add(resultSoundtrack.toList().toBlocking().single());
            result.add(resultMovie.toList().toBlocking().single());

            return result;
        });

        get("/q/b4", (request, response) -> {
            String country = request.param("country").value();

            Object[] params = {country.trim().toLowerCase()};

            Observable<Country> result = model.query(Model.DbClasses.COUNTRY, Model.SQL_B4, params);

            List<Country> countries = result
                    .toList()
                    .toBlocking()
                    .single();

            Country output = countries
                    .stream()
                    .min(Comparator.comparing(c -> c.getCountry().length()))
                    .get();

            String location = getClass().getResource("/R/").getPath() + "plots/b4.png";
            location = location.replaceAll("%20", " ");
            runner.runDb("b4.R", location, output.getCountry_id().toString());

            response.download(new File(location));
        });

        get("/q/b5", () -> {
            //TODO: Implement question B5 here.
            return "NYI";
        });

        get("/q/c2", (request, response) -> {
            String location = getClass().getResource("/R/").getPath() + "plots/c2.png";
            location = location.replaceAll("%20", " ");
            c2Output = runner.runDb("c2.R", location);

            response.download(new File(location));
        });

        get("q/c2/val", () -> {
            if(c2Output == null)
                return "No model found";
            else {
                return new Object[] {
                    c2Output.get(0).asDoubleMatrix(),
                    c2Output.get(1).asDouble()
                };
            }
        });

        get("/q/c4", () -> {
            //TODO: Implement question C4 here.
            return "NYI";
        });

        get("/q/d1", () -> {
            Observable<Country> result = model.query(Model.DbClasses.COUNTRY, Model.SQL_D1);

            return result.toList().toBlocking().single();
        });

        get("/q/d2", req -> {
            String name = req.param("lastname").value() + ", " + req.param("firstname").value();

            Object[] params = {name};

            Observable<Movie> obs = model.query(Model.DbClasses.MOVIE, "SELECT *" +
                    "FROM public.movie" +
                    "WHERE movie_id IN (" +
                    "   SELECT movie_id" +
                    "   FROM public.movie_actor AS ma, public.actor AS a" +
                    "   WHERE ma.actor_id = a.actor_id" +
                    "   AND a.name = ?)" +
                    "ORDER BY release_year", params);

            return obs.toList().toBlocking().single();
        });

        get("/q/movie", req -> {
            String movie = req.param("movie").value();

            Object[] params = {movie};

            Observable<Movie> obs = model.query(Model.DbClasses.MOVIE, Model.SQL_Search_Movie, params);

            return obs.toList().toBlocking().single();
        });

        get("/q/actor", req -> {
            String actor = req.param("actor").value();

            Object[] params = {actor};

            Observable<Actor> obs = model.query(Model.DbClasses.ACTOR, Model.SQL_Search_Actor, params);

            return obs.toList().toBlocking().single();
        });

        //TODO make param optional
        get("/q/movies-by-country", req -> {
            String country = req.param("country").value();

            Object[] params = {country};

            Observable<Country> obs = model.query(Model.DbClasses.COUNTRY, Model.SQL_Movies_by_Country, params);

            return obs.toList().toBlocking().single();
        });
    }

  public static void main(final String[] args) {
    run(App::new, args);
  }
}
