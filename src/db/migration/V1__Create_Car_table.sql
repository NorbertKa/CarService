CREATE TABLE IF NOT EXISTS Car (
  ID SERIAL PRIMARY KEY NOT NULL,
  Manufacturer TEXT NOT NULL,
  Model TEXT NOT NULL,
  RegNumber TEXT NOT NULL UNIQUE
);