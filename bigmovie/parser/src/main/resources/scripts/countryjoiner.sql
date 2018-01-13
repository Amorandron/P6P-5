TRUNCATE TABLE
public.movie_country,
public.gross,
public.country;

DROP INDEX IF EXISTS country_country_id_idx;
DROP INDEX IF EXISTS country_country_idx;

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

CREATE INDEX country_country_id_idx
  ON public.country (country_id);
CREATE INDEX country_country_idx
  ON public.country (country);

INSERT INTO public.movie_country (
  movie_id,
  country_id
)
  SELECT
    get_movie(c.title, c.year, c.type, c.occurence) AS movie_id,
    (SELECT pc.country_id
     FROM public.country pc
     WHERE c.country = pc.country :: TEXT) :: INTEGER AS country_id
  FROM insertion.country c
  WHERE get_movie(c.title, c.year, c.type, c.occurence) IS NOT NULL;