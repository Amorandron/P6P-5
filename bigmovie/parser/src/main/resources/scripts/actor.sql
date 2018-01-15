DROP TABLE IF EXISTS insertion.actor;

DROP INDEX IF EXISTS insertion_actor_full_indx;
DROP INDEX IF EXISTS insertion_actor_half_indx;

CREATE TABLE insertion.actor (
  name TEXT,
  actor_occurence TEXT,
  gender TEXT,
  title TEXT,
  year TEXT,
  type TEXT,
  occurence TEXT,
  role TEXT
);
