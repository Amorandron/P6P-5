\c postgres;
DROP DATABASE IF EXISTS bigmovie;
REASSIGN OWNED BY bigmovie_admin TO postgres;
REASSIGN OWNED BY bigmovie_ro TO postgres;
DROP OWNED BY bigmovie_admin;
DROP OWNED BY bigmovie_ro;
DROP USER IF EXISTS bigmovie_admin;
DROP USER IF EXISTS bigmovie_ro;

-- Create superuser and read only user
CREATE USER bigmovie_admin WITH PASSWORD '***';
CREATE USER bigmovie_ro WITH PASSWORD '***';

CREATE DATABASE bigmovie;

\c bigmovie;

CREATE SCHEMA insertion;

SET transform_null_equals = ON;

-- Revoke all permissions to be reset later
REVOKE ALL ON DATABASE bigmovie FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA insertion FROM PUBLIC;

-- Make users able to connect
GRANT CONNECT ON DATABASE bigmovie TO bigmovie_admin;
GRANT CONNECT ON DATABASE bigmovie TO bigmovie_ro;

GRANT USAGE ON SCHEMA public TO bigmovie_admin;
GRANT USAGE ON SCHEMA public TO bigmovie_ro;
GRANT USAGE ON SCHEMA insertion TO bigmovie_admin;
GRANT USAGE ON SCHEMA insertion TO bigmovie_ro;

-- Give superuser access to everything
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, UPDATE, INSERT, DELETE, TRUNCATE ON TABLES TO bigmovie_admin;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, UPDATE ON SEQUENCES TO bigmovie_admin;
ALTER DEFAULT PRIVILEGES IN SCHEMA insertion
GRANT SELECT, UPDATE, INSERT, DELETE, TRUNCATE ON TABLES TO bigmovie_admin;
ALTER DEFAULT PRIVILEGES IN SCHEMA insertion
GRANT SELECT, UPDATE ON SEQUENCES TO bigmovie_admin;

-- Give read only user only access to selecting
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT ON TABLES TO bigmovie_ro;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT ON SEQUENCES TO bigmovie_ro;
ALTER DEFAULT PRIVILEGES IN SCHEMA insertion
GRANT SELECT ON TABLES TO bigmovie_ro;
ALTER DEFAULT PRIVILEGES IN SCHEMA insertion
GRANT SELECT ON SEQUENCES TO bigmovie_ro;

CREATE SEQUENCE public.movie_id_seq;
CREATE TABLE public.movie (
  movie_id         BIGINT       NOT NULL DEFAULT nextval('movie_id_seq'),
  -- From movies
  title            VARCHAR(511) NOT NULL,
  release_year     INTEGER,
  type             VARCHAR(2),
  occurence        INTEGER      NOT NULL,
  -- From MPAA
  mpaa_rating      VARCHAR(5),
  mpaa_reason      TEXT,
  -- From ratings
  rating           NUMERIC(3, 1),
  rating_votes     INTEGER,
  budget           NUMERIC(30, 2),
  CONSTRAINT movie_pkey
  PRIMARY KEY (movie_id),
  CONSTRAINT movie_uniq
  UNIQUE (title, release_year, type, occurence)
);

CREATE SEQUENCE public.actor_id_seq;
CREATE TABLE public.actor (
  actor_id   BIGINT       NOT NULL DEFAULT nextval('actor_id_seq'),
  -- From actors & actresses
  name       VARCHAR(255) NOT NULL,
  occurence  INTEGER      NOT NULL,
  gender     CHAR(1)      NOT NULL,
  -- From biographies
  birth_date DATE,
  CONSTRAINT actor_pkey
  PRIMARY KEY (actor_id),
  CONSTRAINT actor_uniq
  UNIQUE (name, occurence, gender)
);

CREATE TABLE public.movie_actor (
  movie_id BIGINT NOT NULL,
  actor_id BIGINT NOT NULL,
  role     VARCHAR(255),
  CONSTRAINT actor_movie_pkey
  PRIMARY KEY (movie_id, actor_id),
  CONSTRAINT actor_movie_movie_id_fkey
  FOREIGN KEY (movie_id)
  REFERENCES movie (movie_id),
  CONSTRAINT actor_movie_actor_id_fkey
  FOREIGN KEY (actor_id)
  REFERENCES actor (actor_id)
);

CREATE SEQUENCE public.country_id_seq;
CREATE TABLE public.country (
  country_id INTEGER      NOT NULL DEFAULT nextval('country_id_seq'),
  country    VARCHAR(255) NOT NULL,
  CONSTRAINT country_pkey
  PRIMARY KEY (country_id)
);

CREATE TABLE public.movie_country (
  movie_id   BIGINT  NOT NULL,
  country_id INTEGER NOT NULL,
  CONSTRAINT movie_country_pkey
  PRIMARY KEY (movie_id, country_id),
  CONSTRAINT movie_country_movie_id_fkey
  FOREIGN KEY (movie_id)
  REFERENCES movie (movie_id),
  CONSTRAINT movie_country_country_id_fkey
  FOREIGN KEY (country_id)
  REFERENCES country (country_id)
);

CREATE SEQUENCE public.genre_id_seq;
CREATE TABLE public.genre (
  -- From genres
  genre_id INTEGER      NOT NULL DEFAULT nextval('genre_id_seq'),
  genre    VARCHAR(255) NOT NULL,
  CONSTRAINT genre_pkey
  PRIMARY KEY (genre_id)
);

CREATE TABLE public.movie_genre (
  movie_id BIGINT  NOT NULL,
  genre_id INTEGER NOT NULL,
  CONSTRAINT movie_genre_pkey
  PRIMARY KEY (movie_id, genre_id),
  CONSTRAINT movie_genre_movie_id_fkey
  FOREIGN KEY (movie_id)
  REFERENCES movie (movie_id),
  CONSTRAINT movie_genre_genre_id
  FOREIGN KEY (genre_id)
  REFERENCES genre (genre_id)
);

CREATE SEQUENCE public.soundtrack_id_seq;
CREATE TABLE public.soundtrack (
  -- From soundtracks
  soundtrack_id BIGINT       NOT NULL DEFAULT nextval('soundtrack_id_seq'),
  movie_id      BIGINT       NOT NULL,
  song          VARCHAR(255) NOT NULL,
  CONSTRAINT soundtrack_pkey
  PRIMARY KEY (soundtrack_id),
  CONSTRAINT soundtrack_movie_id_fkey
  FOREIGN KEY (movie_id)
  REFERENCES movie (movie_id)
);

CREATE SEQUENCE public.gross_id_seq;
CREATE TABLE public.gross (
  -- From business
  gross_id         BIGINT NOT NULL DEFAULT nextval('gross_id_seq'),
  movie_id         BIGINT NOT NULL,
  country_id       INTEGER,
  amount           MONEY,
  transaction_date DATE,
  CONSTRAINT gross_pkey
  PRIMARY KEY (gross_id),
  CONSTRAINT gross_movie_id_fkey
  FOREIGN KEY (movie_id)
  REFERENCES movie (movie_id),
  CONSTRAINT gross_country_id_fkey
  FOREIGN KEY (country_id)
  REFERENCES country (country_id)
);

CREATE OR REPLACE FUNCTION get_movie(
  ext_title TEXT,
  ext_year TEXT,
  ext_type TEXT,
  ext_occurence TEXT)
  RETURNS BIGINT AS $movie_id$
BEGIN
  RETURN (SELECT m.movie_id
  FROM movie m
  WHERE m.title :: TEXT = ext_title
    AND (m.release_year :: TEXT = ext_year
       OR m.release_year IS NULL AND ext_year IS NULL)
    AND m.type :: TEXT = ext_type
    AND m.occurence :: TEXT = ext_occurence
    AND m.movie_id IS NOT NULL
  LIMIT 1);
END;
$movie_id$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION count_nulls(
  VARIADIC cols TEXT[])
  RETURNS INTEGER AS $nulls$
DECLARE
  col TEXT;
  no_nulls INTEGER := 0;
BEGIN
  FOREACH col IN ARRAY cols
  LOOP
    IF col IS NULL THEN
      no_nulls := no_nulls + 1;
    END IF;
  END LOOP;
  RETURN no_nulls;
END;
$nulls$ LANGUAGE plpgsql;
