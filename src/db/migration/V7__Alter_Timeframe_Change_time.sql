ALTER TABLE Timeframe
DROP COLUMN TimeFrom;

ALTER TABLE Timeframe
DROP COLUMN TimeTo;

ALTER TABLE Timeframe
ADD COLUMN TimeFrom time NOT NULL;

ALTER TABLE Timeframe
ADD COLUMN TimeTo time NOT NULL;