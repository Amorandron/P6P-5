package com.ykapps.p6p;

import com.github.davidmoten.rx.jdbc.Database;
import com.ykapps.p6p.models.Model;
import com.ykapps.p6p.models.Movie;
import com.ykapps.p6p.models.TestModel;
import javafx.util.Pair;
import org.jooby.Jooby;
import org.jooby.banner.Banner;
import org.jooby.jdbc.Jdbc;
import org.jooby.json.Jackson;
import org.jooby.rx.Rx;
import org.jooby.rx.RxJdbc;
import rx.Observable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author Yannick Kooistra
 */
@SuppressWarnings("unchecked")
public class App extends Jooby {

    {
        use(new Rx());
        use(new Jdbc("db"));

        use(new RxJdbc());

        use(new Jackson());

        use(new Banner("Data Processor"));

        get("/", () -> "Hello World!");

        get("/api", req -> {
            Database db = require(Database.class);

            Model model = new Model(db);

            //noinspection unchecked
            @SuppressWarnings("unchecked")
            Observable<TestModel> obs = model.query(Model.DbClasses.TESTMODEL, "SELECT * FROM test");
            TestModel tm = obs.toBlocking().first();

            return tm;
        });
    }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
