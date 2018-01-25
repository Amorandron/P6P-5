# Author: Jan

file <- "{{param}}";

result <- dbSendQuery(connection, "SELECT total, release_year
    FROM public.movie_country_year
    WHERE country_id = {{param}}");

data <- dbFetch(result, n = -1);

ggplot(data = data, aes(x = release_year, y = total, fill = total)) + geom_bar(stat = "identity");
ggsave(file);