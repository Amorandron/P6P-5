TRUNCATE TABLE
public.movie,
public.soundtrack,
public.gross,
public.movie_actor,
public.movie_country,
public.movie_genre;

DROP INDEX IF EXISTS movie_movie_id_indx;
DROP INDEX IF EXISTS movie_full_movie_indx;

ALTER SEQUENCE public.movie_id_seq RESTART;

INSERT INTO public.movie (
  title,
  release_year,
  type,
  occurence,
  mpaa_rating,
  mpaa_reason,
  rating_votes,
  rating,
  budget
)
  SELECT DISTINCT
    m.title :: VARCHAR(511),
    m.year :: INTEGER,
    m.type :: VARCHAR(2),
    m.occurence :: INTEGER,
    mp.rating :: VARCHAR(5),
    mp.reason,
    r.votes :: INTEGER,
    r.rating :: NUMERIC(3, 1),
    b.budget :: NUMERIC(30, 2)
  FROM insertion.movie m
    LEFT JOIN insertion.mpaa mp
      ON m.title = mp.title
         AND m.year = mp.year
         AND m.type = mp.type
         AND m.occurence = mp.occurence
    LEFT JOIN insertion.ratings r
      ON m.title = r.title
         AND m.year = r.year
         AND m.type = r.type
         AND m.occurence = r.occurence
    LEFT JOIN insertion.business b
      ON m.title = b.title
         AND m.year = b.year
         AND m.type = b.type
         AND m.occurence = b.occurence;

CREATE INDEX movie_movie_id_indx
  ON public.movie (movie_id);
CREATE INDEX movie_full_movie_indx
  ON public.movie (title, release_year, type, occurence);