
CREATE TABLE IF NOT EXISTS users (
    id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(1000) NOT NULL,
    email VARCHAR(1000) NOT NULL UNIQUE,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories (
    id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(100) NOT NULL UNIQUE,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS locations (
    id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat INT NOT NULL,
    lon INT NOT NULL,
    CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilation (
    id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title VARCHAR(50) NOT NULL,
    pinned BOOLEAN,
    CONSTRAINT pk_compilation PRIMARY KEY (id)
);

CREATE TABLE events (
    id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation VARCHAR(2000) NOT NULL,
    category_id INT references categories(id),
    confirmed_requests int,
    created_date TIMESTAMP NOT NULL,
    description VARCHAR(70000) NOT NULL,
    event_date TIMESTAMP NOT NULL,
    initiator_id INT references users(id),
    location_id INT references locations(id),
    paid BOOLEAN,
    participant_limit INT,
    published_date TIMESTAMP,
    request_moderation BOOLEAN,
    state VARCHAR(100) NOT NULL,
    title  VARCHAR(120) NOT NULL,
    views int,
    compilation_id int references compilation(id),
    CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS requests (
    id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event_id INT references events(id),
    requester_id INT references users(id),
    created_date TIMESTAMP NOT NULL,
    status VARCHAR(100) NOT NULL,
    CONSTRAINT pk_request PRIMARY KEY (id),
    UNIQUE (event_id, requester_id)
);


