ALTER TABLE topic ADD COLUMN course_id BIGINT;

ALTER TABLE topic
ADD CONSTRAINT fk_topic_course
FOREIGN KEY(course_id) REFERENCES course(id);
