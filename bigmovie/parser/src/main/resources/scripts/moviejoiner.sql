TRUNCATE TABLE
public.movie,
public.soundtrack,
public.gross,
public.actor_movie,
public.movie_country,
public.movie_genre;

INSERT INTO public.movie (
  title,
  release_year,
  occurence,
  mpaa_rating,
  mpaa_reason,
  rating_votes,
  rating,
  budget
)
  SELECT
    m.title :: VARCHAR(255),
    m.year :: INTEGER,
    m.occurence :: INTEGER,
    mp.rating :: VARCHAR(5),
    mp.reason,
    r.votes :: INTEGER,
    r.rating :: REAL,
    b.budget :: MONEY
  FROM insertion.movie m
    JOIN insertion.mpaa mp
      ON m.title = mp.title
         AND m.year = mp.year
         AND m.occurence = mp.occurence
    JOIN insertion.ratings r
      ON m.title = r.title
         AND m.year = r.year
         AND m.occurence = r.occurence
    JOIN insertion.business b
      ON m.title = b.title
         AND m.year = b.year
         AND m.occurence = b.occurence