CREATE TABLE users(
                      id BIGSERIAL PRIMARY KEY,
                      username VARCHAR(80) NOT NULL,
                      email VARCHAR(155) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      status BOOLEAN NOT NULL DEFAULT TRUE,
                      role_id BIGINT NOT NULL,
                      created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                      updated_at TIMESTAMP,
                      CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES roles(id)
                          ON DELETE RESTRICT
                          ON UPDATE CASCADE
);