CREATE TABLE IF NOT EXISTS hospital (
    id VARCHAR(255),
    name VARCHAR(255) NOT NULL DEFAULT '',
    type VARCHAR(255),
    province_code VARCHAR(64) NOT NULL DEFAULT '',
    city_code VARCHAR(64) NOT NULL DEFAULT '',
    district_code VARCHAR(64) NOT NULL DEFAULT '',
    address VARCHAR(255) NOT NULL DEFAULT '',
    intro VARCHAR(255) NOT NULL DEFAULT '',
    status INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS department (
    id VARCHAR(255),
    hospital_id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL DEFAULT '',
    intro VARCHAR(255) NOT NULL DEFAULT '',
    parent_id VARCHAR(255) NOT NULL DEFAULT '',
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS schedule (
    id INT AUTO_INCREMENT,
    hospital_id VARCHAR(255) NOT NULL,
    department_id VARCHAR(255) NOT NULL,
    title VARCHAR(255),
    doctor_name VARCHAR(255),
    skill VARCHAR(255),
    work_date DATE,
    reserved_number INT NOT NULL,
    available_number INT NOT NULL,
    amount INT,
    status INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
) ENGINE=InnoDB;