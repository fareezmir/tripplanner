CREATE TABLE leg (
    id BIGSERIAL PRIMARY KEY,
    trip_id BIGINT NOT NULL REFERENCES trip(id),
    city TEXT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);