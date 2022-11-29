CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL DEFAULT '',
    name VARCHAR(255) NOT NULL DEFAULT '',
    phone_number VARCHAR(255),
    confirmed_and_active BOOLEAN NOT NULL DEFAULT false,
    member_since TIMESTAMP NOT NULL DEFAULT current_timestamp,
    password_hash VARCHAR(100) DEFAULT '',
    avatar_url VARCHAR(255)
);

CREATE UNIQUE INDEX uni_account_email ON account (email);
CREATE UNIQUE INDEX uni_account_phone_number ON account (phone_number);