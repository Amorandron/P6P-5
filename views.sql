CREATE OR REPLACE VIEW public.actor_rating AS
 SELECT movie_actor.actor_id,
    movie.rating
   FROM movie_actor
     JOIN movie ON movie_actor.movie_id = movie.movie_id;

CREATE OR REPLACE VIEW public.most_used_song_ids AS
  SELECT soundtrack.movie_id
  FROM soundtrack
  WHERE soundtrack.song::text = ((( SELECT soundtrack_1.song
                                    FROM soundtrack soundtrack_1
                                    GROUP BY soundtrack_1.song
                                    ORDER BY (count(soundtrack_1.song)) DESC
                                    LIMIT 1))::text);

CREATE OR REPLACE VIEW public.gross_by_period AS
  SELECT gross.gross_id,
    gross.movie_id,
    gross.country_id,
    gross.amount,
    gross.transaction_date,
    low.low_transaction_date,
    gross.transaction_date - low.low_transaction_date AS transaction_date_delta
  FROM gross
    JOIN ( SELECT cur.movie_id,
             cur.country_id,
             cur.transaction_date AS low_transaction_date
           FROM gross cur
           WHERE NOT (EXISTS ( SELECT low_1.gross_id,
                                 low_1.movie_id,
                                 low_1.country_id,
                                 low_1.amount,
                                 low_1.transaction_date
                               FROM gross low_1
                               WHERE low_1.movie_id = cur.movie_id AND low_1.country_id = cur.country_id AND low_1.transaction_date < cur.transaction_date)) AND cur.transaction_date IS NOT NULL AND cur.amount IS NOT NULL) low ON gross.movie_id = low.movie_id AND gross.country_id = low.country_id
  WHERE gross.transaction_date IS NOT NULL AND gross.transaction_date <> low.low_transaction_date;