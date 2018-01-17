file <- "{{param}}";

result <- dbSendQuery(connection, "SELECT count(movie_id), release_year
    FROM public.movie
    WHERE movie_id in (
        SELECT movie_id
        FROM public.movie_country
        WHERE country_id = {{param}})
    GROUP BY release_year
    HAVING release_year BETWEEN {{param}} AND {{param}}
    ORDER BY release_year ASC");

data <- dbFetch(result, n = -1){{join}};

ggplot(data = data, aes(x = release_year, y = count, fill = count)) + geom_bar(stat = "identity");
ggsave(file);