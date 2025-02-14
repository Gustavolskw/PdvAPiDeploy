CREATE TABLE product_type (
                              id BIGSERIAL PRIMARY KEY,
                              name VARCHAR(50) NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                              updated_at TIMESTAMP
);