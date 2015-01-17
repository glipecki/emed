ALTER TABLE observations ADD COLUMN timestamp TIMESTAMP;
UPDATE observations SET timestamp = now();
ALTER TABLE observations ALTER COLUMN timestamp SET NOT NULL;
