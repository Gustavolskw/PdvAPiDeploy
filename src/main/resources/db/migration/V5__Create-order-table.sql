CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        consumer_name VARCHAR(150) NOT NULL,
                        table_number INT NOT NULL,
                        guide BIGINT NOT NULL,
                        status BOOLEAN NOT NULL DEFAULT TRUE,
                        created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                        updated_at TIMESTAMP,
                        CONSTRAINT fk_guider FOREIGN KEY(guide) REFERENCES users(id)
                            ON DELETE RESTRICT
                            ON UPDATE CASCADE
);