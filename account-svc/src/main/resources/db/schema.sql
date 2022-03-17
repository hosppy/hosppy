CREATE TABLE IF NOT EXISTS account (
    id VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL DEFAULT '',
    phone_number VARCHAR(255) NOT NULL,
    confirmed_and_active BOOLEAN NOT NULL DEFAULT false,
    member_since TIMESTAMP NOT NULL DEFAULT current_timestamp,
    password_hash VARCHAR(100) DEFAULT '',
    photo_url VARCHAR(255) NOT NULL,
    support BOOLEAN NOT NULL DEFAULT false,
    PRIMARY KEY (id),
    UNIQUE uni_account_email (email),
    UNIQUE uni_account_phone_number (phone_number)
) ENGINE=InnoDB;
