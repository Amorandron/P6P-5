package com.ykapps.p6p;

import com.ykapps.p6p.models.Movie;
import javafx.util.Pair;
import org.jooby.Jooby;
import org.jooby.jdbc.Jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author Yannick Kooistra
 */
public class App extends Jooby {
    {
        use(new Jdbc("db"));

        get("/", () -> "Hello World!");

        get("/api", req -> {
            DataSource db = require(DataSource.class);

            Movie movieModel = new Movie(db);

            List<List<Pair>> results = movieModel.get("id=?");

            String data = "";


            for(List<Pair> result : results) {
                Pair<String, Integer> idPair = result.stream()
                        .filter(pair -> pair.getKey().equals("ID"))
                        .findFirst()
                        .get();

                int id = idPair.getValue();

                Pair<String, String> namePair = result.stream()
                        .filter(pair -> pair.getKey().equals("NAME"))
                        .findFirst()
                        .get();

                String name = namePair.getValue();

                data += "<li>" + id + ": " + name + "</li>";
            }

            String html = "<html><head></head><body><ul>" + data + "</ul></body></html>";

        return html;
        });
    }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
