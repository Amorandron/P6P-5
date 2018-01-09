DROP TABLE IF EXISTS insertion.rating;

CREATE TABLE insertion.rating(
  title TEXT,
  occurence TEXT,
  year TEXT,
  votes TEXT,
  rating TEXT
);

COPY insertion.rating
  FROM '{{csv}}' (
  FORMAT CSV,
  DELIMITER '{{delimiter}}',
  QUOTE '"',
  ESCAPE '\'
);