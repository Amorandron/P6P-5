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