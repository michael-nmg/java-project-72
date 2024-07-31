-- H2 syntax
DROP TABLE IF EXISTS url_checks, urls;

DROP SEQUENCE IF EXISTS url_seq;
DROP SEQUENCE IF EXISTS url_check_seq;

CREATE SEQUENCE url_seq;
CREATE TABLE urls
(
    id         INT DEFAULT NEXT VALUE FOR url_seq UNIQUE NOT NULL,
    name       VARCHAR(255) UNIQUE                       NOT NULL,
    created_at TIMESTAMP                                 NOT NULL
);

CREATE SEQUENCE url_check_seq;
CREATE TABLE url_checks
(
    id          INT DEFAULT NEXT VALUE FOR url_check_seq UNIQUE NOT NULL,
    url_id      INT REFERENCES urls (id) ON DELETE CASCADE      NOT NULL,
    status_code INTEGER                                         NOT NULL,
    created_at  TIMESTAMP                                       NOT NULL,
    h1          VARCHAR(255),
    title       VARCHAR(255),
    description TEXT
);
