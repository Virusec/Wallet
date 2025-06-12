-- liquibase formatted sql

-- changeset AnatioliyShikin:v1.001.1
-- comment: Add table wallet
CREATE TABLE wallet
(
    id         UUID PRIMARY KEY,
    balance           DECIMAL   NOT NULL DEFAULT 0,
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);