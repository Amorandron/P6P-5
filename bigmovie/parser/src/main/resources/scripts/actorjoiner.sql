TRUNCATE TABLE
public.movie_actor,
public.actor;

DROP TABLE IF EXISTS insertion.birth;

CREATE TABLE insertion.birth (
  name TEXT,
  occurence TEXT,
  birth_date TEXT
);

INSERT INTO insertion.birth (
  name,
  occurence,
  birth_date
)
  SELECT b.NM, b.OC, b.DB
  FROM insertion.biography AS b
  WHERE b.DB <> '';

CREATE INDEX insertion_actor_full_indx
  ON insertion.actor(name, actor_occurence, gender);

CREATE INDEX insertion_actor_half_indx
  ON insertion.actor(name, actor_occurence);

CREATE INDEX insertion_birth_full_indx
  ON insertion.birth(name, occurence);

ALTER SEQUENCE public.actor_id_seq RESTART;

DROP INDEX IF EXISTS actor_full_actor_indx;

INSERT INTO public.actor (
  name,
  occurence,
  gender,
  birth_date
)
  SELECT DISTINCT a.name :: VARCHAR(255),
    a.actor_occurence :: INTEGER,
    a.gender :: CHAR(1),
    b.birth_date :: DATE
  FROM insertion.actor AS a
  LEFT JOIN insertion.birth AS b
    ON a.name = b.name
      AND a.actor_occurence = b.occurence;

CREATE INDEX actor_full_actor_indx
  ON public.actor(name, occurence, gender);

INSERT INTO public.movie_actor (
  movie_id,
  actor_id,
  role
)
  SELECT DISTINCT
    get_movie(a.title, a.year, a.type, a.occurence) AS movie_id,
    (SELECT pa.actor_id
     FROM public.actor AS pa
     WHERE a.name = pa.name :: TEXT
        AND a.actor_occurence = pa.occurence :: TEXT
        AND a.gender = pa.gender :: TEXT) :: BIGINT AS actor_id,
    a.role :: VARCHAR(255)
  FROM insertion.actor a
  WHERE get_movie(a.title, a.year, a.type, a.actor_occurence) IS NOT NULL;