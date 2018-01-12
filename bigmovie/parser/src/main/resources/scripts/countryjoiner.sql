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

CREATE INDEX country_country_id_idx ON public.country (country_id);
CREATE INDEX country_country_idx ON public.country (country);

INSERT INTO public.movie_country (
  movie_id,
  country_id
)
  SELECT
    (SELECT m.movie_id
     FROM public.movie m
     WHERE m.title :: TEXT = c.title
           AND (m.release_year :: TEXT = c.year or (m.release_year is null and c.year is null))
           AND m.type :: TEXT = c.type
           AND m.occurence :: TEXT = c.occurence) :: BIGINT AS movie_id,
    (SELECT pc.country_id
     FROM public.country pc
     WHERE c.country :: TEXT = pc.country) :: INTEGER AS country_id
  FROM insertion.country c;
