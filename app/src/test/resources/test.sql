-- H2 syntax
DROP TABLE IF EXISTS url_checks, urls;

CREATE TABLE urls
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP           NOT NULL
);

CREATE TABLE url_checks
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    url_id      INT REFERENCES urls (id) ON DELETE CASCADE NOT NULL,
    status_code INTEGER                                    NOT NULL,
    created_at  TIMESTAMP                                  NOT NULL,
    h1          VARCHAR(255),
    title       VARCHAR(255),
    description TEXT
);
