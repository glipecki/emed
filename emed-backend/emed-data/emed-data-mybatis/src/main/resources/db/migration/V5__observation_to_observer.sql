ALTER TABLE observations ADD COLUMN observer INT;
UPDATE observations SET observer = 1;
ALTER TABLE observations ADD FOREIGN KEY (observer) REFERENCES users (user_id);