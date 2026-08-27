CREATE TABLE day (
    id BIGSERIAL PRIMARY KEY,
    leg_id BIGINT NOT NULL REFERENCES leg(id),
    day_date DATE NOT NULL,
    start_lat DOUBLE PRECISION NOT NULL,
    start_lon DOUBLE PRECISION NOT NULL,
    end_lat DOUBLE PRECISION,
    end_lon DOUBLE PRECISION,
    start_time TIME NOT NULL,
    latest_end_time TIME NOT NULL,
    max_minutes_per_day INTEGER NOT NULL
);