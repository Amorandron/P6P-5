TRUNCATE TABLE
public.movie_genre,
public.genre;

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

INSERT INTO public.movie_genre (
  movie_id,
  genre_id
)
  SELECT
    m.movie_id,
    id
  FROM public.movie m
    JOIN insertion.genre g
      ON g.title = m.title :: TEXT
         AND g.year = m.release_year :: TEXT
         AND g.type = m.type :: TEXT
         AND g.occurence = m.occurence :: TEXT
  WHERE id = (SELECT pg.genre_id
                      FROM public.genre pg
                      WHERE pg.genre = g.genre
  );