-- Author:
-- Jan

TRUNCATE TABLE
public.movie_genre,
public.genre;

DROP INDEX IF EXISTS genre_genre_id_indx;
DROP INDEX IF EXISTS genre_genre_indx;

INSERT INTO public.movie (
  title,
  release_year,
  type,
  occurence
)
  SELECT g.title :: VARCHAR(511),
    g.year :: INTEGER,
    g.type :: VARCHAR(2),
    g.occurence :: INTEGER
  FROM insertion.genre AS g
  GROUP BY g.title, g.year, g.type, g.occurence
  HAVING get_movie(g.title, g.year, g.type, g.occurence) IS NULL;

ALTER SEQUENCE public.genre_id_seq RESTART;

INSERT INTO public.genre (
  genre
)
  SELECT DISTINCT initcap(g.genre) :: VARCHAR(255)
  FROM insertion.genre AS g;

CREATE INDEX genre_genre_id_indx
  ON public.genre (genre_id);
CREATE INDEX genre_genre_indx
  ON public.genre (genre);

INSERT INTO public.movie_genre (
  movie_id,
  genre_id
)
  SELECT DISTINCT
    get_movie(g.title, g.year, g.type, g.occurence) AS movie_id,
    (SELECT pg.genre_id
     FROM public.genre AS pg
     WHERE initcap(g.genre) = pg.genre :: TEXT) :: INTEGER AS genre_id
  FROM insertion.genre AS g
  WHERE get_movie(g.title, g.year, g.type, g.occurence) IS NOT NULL;

REFRESH MATERIALIZED VIEW public.movie_genre_year;