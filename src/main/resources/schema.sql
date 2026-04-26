CREATE TABLE IF NOT EXISTS event_attendees (
    event_id INT NOT NULL,
    user_id  INT NOT NULL,
    PRIMARY KEY (event_id, user_id)
);
