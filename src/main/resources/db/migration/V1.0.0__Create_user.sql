CREATE TABLE IF NOT EXISTS user_principal
(
    id         UUID         NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    email_id   VARCHAR(100) NOT NULL,
    phone_no   VARCHAR(100) NOT NULL,
    created_by TIMESTAMP DEFAULT NOW(),
    CONSTRAINT pk_user_principal PRIMARY KEY (id)
);