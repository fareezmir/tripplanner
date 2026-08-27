CREATE TABLE stop (
    id BIGSERIAL PRIMARY KEY,
    day_id BIGINT REFERENCES day(id),
    name TEXT NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    dwell_minutes INTEGER NOT NULL,
    dwell_source TEXT NOT NULL DEFAULT 'ESTIMATED',
    earliest_start TIME,
    latest_start TIME
);