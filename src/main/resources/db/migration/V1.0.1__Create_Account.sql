CREATE TABLE IF NOT EXISTS account
(
    id         UUID         NOT NULL,
    account_id VARCHAR(100) NOT NULL,
    created_by TIMESTAMP DEFAULT NOW(),
    CONSTRAINT pk_account PRIMARY KEY (id)
);