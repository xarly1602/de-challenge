DROP TABLE IF EXISTS rank CASCADE;
DROP TABLE IF EXISTS console CASCADE;
DROP TABLE IF EXISTS company CASCADE;

CREATE TABLE company (
    id BIGINT auto_increment NOT NULL PRIMARY KEY,
    name VARCHAR(32)
);

CREATE TABLE console (
    id BIGINT auto_increment NOT NULL PRIMARY KEY,
    name VARCHAR(32),
    company BIGINT,
    FOREIGN KEY(company) REFERENCES company(id)
);

CREATE TABLE rank (
    id BIGINT auto_increment NOT NULL PRIMARY KEY,
    metascore INT,
    name VARCHAR(256),
    console BIGINT,
    userscore VARCHAR(8),
    date VARCHAR(32),
    FOREIGN KEY(console) REFERENCES console(id)
);
