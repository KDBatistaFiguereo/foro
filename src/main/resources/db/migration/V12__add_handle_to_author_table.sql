BEGIN;
ALTER TABLE author ADD COLUMN handle TEXT;
UPDATE author SET handle = 'changeme' WHERE handle IS NULL;
ALTER TABLE author ALTER COLUMN handle SET NOT NULL;
COMMIT;
