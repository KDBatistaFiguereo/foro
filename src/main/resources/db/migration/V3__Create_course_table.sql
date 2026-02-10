CREATE TABLE course (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  course_name TEXT CHECK ( length(course_name) > 0 )
);
