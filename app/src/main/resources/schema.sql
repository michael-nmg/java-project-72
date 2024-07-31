-- PSQL syntax
DROP TABLE IF EXISTS url_checks, urls;

CREATE TABLE urls
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP           NOT NULL
);

CREATE TABLE url_checks
(
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    url_id      BIGINT REFERENCES urls (id) ON DELETE CASCADE NOT NULL,
    status_code INTEGER                                       NOT NULL,
    created_at  TIMESTAMP                                     NOT NULL,
    h1          VARCHAR(255),
    title       VARCHAR(255),
    description TEXT
);
