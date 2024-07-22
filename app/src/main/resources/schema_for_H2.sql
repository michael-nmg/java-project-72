-- H2 syntax
DROP TABLE IF EXISTS urls;
CREATE SEQUENCE url_seq;

CREATE TABLE urls
(
    id         INT DEFAULT NEXT VALUE FOR url_seq NOT NULL,
    name       VARCHAR(255) UNIQUE                NOT NULL,
    created_at TIMESTAMP                          NOT NULL
);
