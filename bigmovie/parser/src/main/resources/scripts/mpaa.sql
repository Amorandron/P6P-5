DROP TABLE IF EXISTS insertion.mpaa;

CREATE TABLE insertion.mpaa(
  title TEXT,
  occurence TEXT,
  year TEXT,
  rating TEXT,
  reason TEXT
);

COPY insertion.mpaa
  FROM '{{csv}}' (
  FORMAT CSV,
  DELIMITER '{{delimiter}}',
  QUOTE '"',
  ESCAPE '\'
);