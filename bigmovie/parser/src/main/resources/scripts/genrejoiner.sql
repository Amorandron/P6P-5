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
    get_movie(g.title, g.year, g.type, g.occurence) AS movie_id,
    (SELECT pg.genre_id
     FROM public.genre pg
     WHERE g.genre = pg.genre :: TEXT) :: INTEGER AS genre_id
  FROM insertion.genre g
  WHERE get_movie(g.title, g.year, g.type, g.occurence) IS NOT NULL;