CREATE TABLE IF NOT EXISTS Run (
    id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    started_on TIMESTAMP NOT NULL,
    completed_on TIMESTAMP NOT NULL,
    kilometers INT NOT NULL,
    location VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);