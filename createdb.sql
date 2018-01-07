\c bigmovie;

-- Reset queries;
DROP TABLE IF EXISTS countries;
DROP SEQUENCE IF EXISTS country_id_seq;
DROP TABLE IF EXISTS genres;
DROP SEQUENCE IF EXISTS genre_id_seq;
DROP TABLE IF EXISTS soundtracks;
DROP SEQUENCE IF EXISTS soundtrack_id_seq;
DROP TABLE IF EXISTS actors_movies;
DROP TABLE IF EXISTS movies;
DROP SEQUENCE IF EXISTS movie_id_seq;
DROP TABLE IF EXISTS actors;
DROP SEQUENCE IF EXISTS actor_id_seq;

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

-- Revoke all permissions to be reset later
REVOKE ALL ON DATABASE bigmovie FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM PUBLIC;

-- Make users able to connect
GRANT CONNECT ON DATABASE bigmovie TO bigmovie_admin;
GRANT CONNECT ON DATABASE bigmovie TO bigmovie_ro;

GRANT USAGE ON SCHEMA public TO bigmovie_admin;
GRANT USAGE ON SCHEMA public TO bigmovie_ro;

-- Give superuser access to everything
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLES TO bigmovie_admin;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, UPDATE ON SEQUENCES TO bigmovie_admin;

-- Give read only user only access to selecting
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT ON TABLES TO bigmovie_ro;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT ON SEQUENCES TO bigmovie_ro;

CREATE SEQUENCE movie_id_seq;
CREATE TABLE movies (
  movie_id     BIGINT       NOT NULL DEFAULT nextval('movie_id_seq'),
  -- From movies
  title        VARCHAR(255) NOT NULL,
  release_year INTEGER,
  occurance    INTEGER      NOT NULL,
  -- From MPAA
  mpaa_rating  VARCHAR(5),
  mpaa_reason  TEXT,
  -- From ratings
  rating       INTEGER,
  rating_votes INTEGER,
  CONSTRAINT movies_pkey
  PRIMARY KEY (movie_id),
  CONSTRAINT movies_uniq
  UNIQUE (title, release_year, occurance)
);

CREATE SEQUENCE actor_id_seq;
CREATE TABLE actors (
  actor_id   BIGINT       NOT NULL DEFAULT nextval('actor_id_seq'),
  -- From actors & actresses
  name       VARCHAR(255) NOT NULL,
  occurance  INTEGER      NOT NULL,
  gender     CHAR(1)      NOT NULL,
  -- From biographies
  birth_date DATE,
  CONSTRAINT actors_pkey
  PRIMARY KEY (actor_id),
  CONSTRAINT actors_uniq
  UNIQUE (name, occurance)
);

CREATE TABLE actors_movies (
  movie_id BIGINT NOT NULL,
  actor_id BIGINT NOT NULL,
  role     VARCHAR(255),
  CONSTRAINT actors_movies_pkey
  PRIMARY KEY (movie_id, actor_id),
  CONSTRAINT actors_movies_movie_id_fkey
  FOREIGN KEY (movie_id)
  REFERENCES movies (movie_id),
  CONSTRAINT actors_movies_actor_id_fkey
  FOREIGN KEY (actor_id)
  REFERENCES actors (actor_id)
);

CREATE SEQUENCE country_id_seq;
CREATE TABLE countries (
  -- From countries
  country_id BIGINT       NOT NULL DEFAULT nextval('country_id_seq'),
  movie_id   BIGINT       NOT NULL,
  country    VARCHAR(255) NOT NULL,
  CONSTRAINT countries_pkey
  PRIMARY KEY (country_id),
  CONSTRAINT countries_movie_id_fkey
  FOREIGN KEY (movie_id)
  REFERENCES movies (movie_id)
);

CREATE SEQUENCE genre_id_seq;
CREATE TABLE genres (
  -- From genres
  genre_id BIGINT       NOT NULL DEFAULT nextval('genre_id_seq'),
  movie_id BIGINT       NOT NULL,
  genre    VARCHAR(255) NOT NULL,
  CONSTRAINT genres_pkey
  PRIMARY KEY (genre_id),
  CONSTRAINT genres_movie_id_fkey

  FOREIGN KEY (movie_id)
  REFERENCES movies (movie_id)

);

CREATE SEQUENCE soundtrack_id_seq;
CREATE TABLE soundtracks (
  -- From soundtracks
  soundtrack_id BIGINT       NOT NULL DEFAULT nextval('soundtrack_id_seq'),
  movie_id      BIGINT       NOT NULL,
  song          VARCHAR(255) NOT NULL,
  CONSTRAINT soundtracks_pkey
  PRIMARY KEY (soundtrack_id),
  CONSTRAINT soundtracks_movie_id_fkey
  FOREIGN KEY (movie_id)
  REFERENCES movies (movie_id)
);
