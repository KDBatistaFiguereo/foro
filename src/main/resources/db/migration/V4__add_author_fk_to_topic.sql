ALTER TABLE topic ADD COLUMN author_id BIGINT;

ALTER TABLE topic
ADD CONSTRAINT fk_topic_author
FOREIGN KEY(author_id) REFERENCES author(id);
