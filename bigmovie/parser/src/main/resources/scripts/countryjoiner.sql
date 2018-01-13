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

 SELECT ( SELECT m.movie_id
           FROM movie m
          WHERE m.title::text = c.title AND (m.release_year::text = c.year OR m.release_year IS NULL AND c.year IS NULL) AND m.type::text = c.type AND m.occurence::text = c.occurence AND m.movie_id IS NOT NULL
         LIMIT 1) AS movie_id,
    ( SELECT pc.country_id
           FROM country pc
          WHERE c.country = pc.country::text) AS country_id
   FROM insertion.country c
  WHERE (( SELECT m.movie_id
           FROM movie m
          WHERE m.title::text = c.title AND (m.release_year::text = c.year OR m.release_year IS NULL AND c.year IS NULL) AND m.type::text = c.type AND m.occurence::text = c.occurence AND m.movie_id IS NOT NULL
         LIMIT 1)) IS NOT NULL;
