DROP INDEX IF EXISTS soundtrack_movie_id_indx;

ALTER SEQUENCE public.soundtrack_id_seq RESTART;

INSERT INTO public.soundtrack (
  movie_id,
  song
)
  SELECT
    (get_movie(s.title, s.year, s.type, s.occurence)) AS movie_id,
    initcap(s.song) :: VARCHAR(255)
  FROM insertion.soundtrack s
  WHERE get_movie(s.title, s.year, s.type, s.occurence) IS NOT NULL;

CREATE INDEX soundtrack_movie_id_indx
  ON public.soundtrack(movie_id);