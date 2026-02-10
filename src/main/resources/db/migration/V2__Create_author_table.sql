CREATE TABLE author(
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  user_name TEXT CHECK (length(user_name) > 0)
);
