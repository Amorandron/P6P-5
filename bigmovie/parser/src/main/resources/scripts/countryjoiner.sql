TRUNCATE TABLE
public.movie_country,
public.gross,
public.country;

INSERT INTO public.country (
  country
)
  SELECT DISTINCT c.country :: VARCHAR(255)
  FROM insertion.country c
  WHERE (c.country) NOT IN (
    SELECT pc.country
    FROM public.country pc
    WHERE pc.country = c.country
  );

INSERT INTO public.movie_country (
  movie_id,
  country_id
)
  SELECT
    m.movie_id,
    id
  FROM public.movie m
    JOIN insertion.country c
      ON c.title = m.title :: TEXT
         AND c.year = m.release_year :: TEXT
         AND c.occurence = m.occurence :: TEXT
  WHERE id = (SELECT pc.country_id
                      FROM public.country pc
                      WHERE pc.country = c.country
  );