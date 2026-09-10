CREATE TABLE feed (
    feed_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    source VARCHAR(100) NOT NULL,
    description TEXT,
    format VARCHAR(20) NOT NULL,
    schedule_rule TEXT NOT NULL,
    schedule_timezone VARCHAR(30) DEFAULT 'America/Chicago'
)