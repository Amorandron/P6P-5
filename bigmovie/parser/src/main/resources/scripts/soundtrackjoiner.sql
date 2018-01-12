INSERT INTO public.soundtrack (
  movie_id,
  song
)
  SELECT
    (SELECT m.movie_id
     FROM public.movie m
     WHERE m.title :: TEXT = s.title
     AND m.release_year :: TEXT = s.year
     AND m.type :: TEXT = s.type
     AND m.occurence :: TEXT = s.occurence) :: BIGINT AS movie_id,
    s.song :: VARCHAR(255)
  FROM insertion.soundtrack s;