DROP TABLE IF EXISTS insertion.movie;

CREATE TABLE insertion.movie(
  title TEXT,
  occurence TEXT,
  year TEXT
);

COPY insertion.movie
FROM '{{csv}}' (
  FORMAT CSV,
  DELIMITER '{{delimiter}}',
  QUOTE '"',
  ESCAPE '\'
);


