# Author: Everdien

file <- "{{param}}";

result <- dbSendQuery(connection, "WITH genre_nums AS (
                                      SELECT SUM(mgy.movie_count) AS total, mgy.release_year AS release_year
                                                        FROM movie_genre_year AS mgy
                                                        WHERE mgy.genre_id IS NOT NULL
                                                        GROUP BY mgy.release_year
                                  )
                                  SELECT
                                  (SELECT (SUM(mgy.movie_count) :: DECIMAL / (
                                  SELECT gn.total
                                  FROM genre_nums gn
                                  WHERE gn.release_year = mgy.release_year
                                  ) :: DECIMAL)) :: DOUBLE PRECISION  * 100 AS percentage,
                                  release_year,
                                  genre
                                  FROM movie_genre_year AS mgy
                                  LEFT JOIN public.genre AS g
                                  ON mgy.genre_id = g.genre_id
                                  WHERE genre IS NOT NULL
                                  AND release_year BETWEEN 1884 AND {{param}}
                                  GROUP BY mgy.release_year, g.genre
                                  ORDER BY mgy.release_year, g.genre ASC");

data <- dbFetch(result, n = -1);

ggplot(data = data, aes(x = release_year, y = percentage, fill = genre)) + geom_bar(stat = "identity") + scale_fill_manual(values=c("#0395FA","#FF2C2C","#FEFF00","#00FF1C","#FF00E3","#000EA9","#C90000","#A39D00","#0D7600","#7C0074","#8080FA","#FF8686","#FCF179","#8DFF84","#DE77FF","#414489","#B65656","#9D9E54","#4F8D53","#85579C","#0395FA","#FF2C2C","#FEFF00","#00FF1C","#FF00E3","#000EA9","#C90000","#A39D00","#0D7600","#7C0074","#8080FA","#FF8686","#FCF179","#8DFF84","#DE77FF","#414489","#B65656","#9D9E54","#4F8D53","#85579C"));
ggsave(file);