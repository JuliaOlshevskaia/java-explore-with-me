
CREATE TABLE IF NOT EXISTS endpoint_view (
    id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    app VARCHAR(1000) NOT NULL,
    uri VARCHAR(1000) NOT NULL,
    ip VARCHAR(1000) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);


