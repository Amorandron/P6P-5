DROP INDEX IF EXISTS gross_gross_id_idx;
DROP INDEX IF EXISTS gross_movie_id_idx;
DROP INDEX IF EXISTS gross_country_id_idx;

INSERT INTO public.movie (
  title,
  release_year,
  type,
  occurence
)
  SELECT b.title :: VARCHAR(511),
    b.year :: INTEGER,
    b.type :: VARCHAR(2),
    b.occurence :: INTEGER
  FROM insertion.business AS b
  GROUP BY b.title, b.year, b.type, b.occurence
  HAVING get_movie(b.title, b.year, b.type, b.occurence) IS NULL;

ALTER SEQUENCE public.gross_id_seq RESTART;

INSERT INTO public.gross (
  movie_id,
  country_id,
  amount,
  transaction_date
)
  SELECT
    (get_movie(b.title, b.year, b.type, b.occurence)) AS movie_id,
    (SELECT c.country_id
     FROM public.country c
     WHERE c.country :: TEXT = b.country) :: INTEGER AS country_id,
    b.gross :: MONEY,
    to_date(b.date, 'DDMMYYYY')
  FROM insertion.business b
  WHERE count_nulls(get_movie(b.title, b.year, b.type, b.occurence) :: TEXT,
                   (SELECT c.country_id
                    FROM public.country c
                    WHERE c.country :: TEXT = b.country) :: TEXT,
                    b.gross :: TEXT,
                    to_date(b.date, 'DDMMYYYY') :: TEXT) < 3;

CREATE INDEX gross_gross_id_idx
  ON public.gross (gross_id);
CREATE INDEX gross_movie_id_idx
  ON public.gross (movie_id);
CREATE INDEX gross_country_id_idx
  ON public.gross (country_id);