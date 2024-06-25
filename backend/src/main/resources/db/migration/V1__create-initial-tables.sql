CREATE TABLE TB_MARITAL_STATUS (
    id SERIAL PRIMARY KEY NOT NULL,
    status VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE TB_ROLE (
    id SERIAL PRIMARY KEY NOT NULL,
    role VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE TB_DEPARTMENT (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE TB_FUNCTION (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE TB_ADDRESS (
    id SERIAL PRIMARY KEY,
    country VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE TB_PASSWORD(
    id SERIAL PRIMARY KEY NOT NULL,
    password VARCHAR NOT NULL,
    is_temporary BOOLEAN NOT NULL,
    last_change_dth TIMESTAMP NOT NULL
);

CREATE TABLE TB_USER (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(13) NOT NULL UNIQUE,
    address_id INTEGER NOT NULL,
    department_id INTEGER NOT NULL,
    function_id INTEGER NOT NULL,
    password_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    marital_status_id INTEGER NOT NULL,
    hiring_dt DATE NOT NULL,
    resignation_dt DATE,
    manager_id INTEGER,
    birth_dt DATE NOT NULL,
    CONSTRAINT tb_user_address_fk FOREIGN KEY (address_id) REFERENCES TB_ADDRESS(id),
    CONSTRAINT tb_user_department_fk FOREIGN KEY (department_id) REFERENCES TB_DEPARTMENT(id),
    CONSTRAINT tb_user_function_fk FOREIGN KEY (function_id) REFERENCES TB_FUNCTION(id),
    CONSTRAINT tb_user_password_fk FOREIGN KEY (password_id) REFERENCES TB_PASSWORD(id),
    CONSTRAINT tb_user_marital_status_fk FOREIGN KEY (marital_status_id) REFERENCES TB_MARITAL_STATUS(id),
    CONSTRAINT tb_user_role_fk FOREIGN KEY (role_id) REFERENCES TB_ROLE(id),
    CONSTRAINT tb_user_manager_fk FOREIGN KEY (manager_id) REFERENCES TB_USER(id)
);
