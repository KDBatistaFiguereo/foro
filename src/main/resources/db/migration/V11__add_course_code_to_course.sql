BEGIN;
ALTER TABLE course ADD COLUMN course_code TEXT;
--Temporary code
UPDATE course SET course_code = 'TEM-0000' WHERE course_code IS NULL;

ALTER TABLE course ALTER COLUMN course_code SET NOT NULL;
COMMIT;
