INSERT INTO public.soundtrack (
  movie_id,
  song
)
  SELECT
    (get_movie(s.title, s.year, s.type, s.occurence)) AS movie_id,
    s.song :: VARCHAR(255)
  FROM insertion.soundtrack s
  WHERE get_movie(s.title, s.year, s.type, s.occurence) IS NOT NULL;