CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          type_id BIGINT NOT NULL,
                          name VARCHAR(50) NOT NULL,
                          description TEXT NOT NULL,
                          value DECIMAL(15,2) NOT NULL,
                          image_url VARCHAR(255),
                          status BOOLEAN NOT NULL DEFAULT TRUE,
                          created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                          updated_at TIMESTAMP,
                          CONSTRAINT fk_type FOREIGN KEY(type_id) REFERENCES product_type(id)
                              ON DELETE RESTRICT
                              ON UPDATE CASCADE
);