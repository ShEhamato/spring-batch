DROP TABLE people IF EXISTS;

CREATE TABLE Item  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    item_name VARCHAR(40),
    item_code VARCHAR(40)
);
