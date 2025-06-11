-- liquibase formatted sql

-- changeset AnatioliyShikin:v1.002.1
-- comment: Add table wallet_operation
CREATE TABLE wallet_operation
(
    id      BIGSERIAL PRIMARY KEY,
    wallet_id         UUID        NOT NULL,
    amount            DECIMAL     NOT NULL,
    operation_type    VARCHAR(10) NOT NULL,
    registration_date TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wallet_operation_wallet FOREIGN KEY (wallet_id) REFERENCES wallet (id)
);