CREATE TABLE topic (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    public_id UUID,
    title TEXT CHECK (length(title) > 0),
    body TEXT CHECK (length(body) > 0)
);
