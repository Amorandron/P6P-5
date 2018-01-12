TRUNCATE TABLE
public.movie_genre,
public.genre;

DROP INDEX IF EXISTS genre_genre_id_indx;
DROP INDEX IF EXISTS genre_genre_indx;

INSERT INTO public.genre (
  genre
)
  SELECT DISTINCT g.genre :: VARCHAR(255)
  FROM insertion.genre g
  WHERE (g.genre) NOT IN (
    SELECT pg.genre
    FROM public.genre pg
    WHERE pg.genre = g.genre
  );

CREATE INDEX genre_genre_id_indx ON public.genre (genre_id);
CREATE INDEX genre_genre_indx ON public.genre (genre);

INSERT INTO public.movie_genre (
  movie_id,
  genre_id
)
SELECT
  (SELECT m.movie_id
   FROM public.movie m
   WHERE m.title :: TEXT = g.title
         AND m.release_year :: TEXT = g.year
         AND m.type :: TEXT = g.type
         AND m.occurence :: TEXT = g.occurence) :: BIGINT AS movie_id,
  (SELECT pg.genre_id
   FROM public.genre pg
   WHERE g.genre :: TEXT = pg.genre) :: INTEGER AS genre_id
FROM insertion.genre g;