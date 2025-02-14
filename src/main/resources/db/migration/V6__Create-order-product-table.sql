CREATE TABLE product_order (
                               order_id BIGINT NOT NULL,
                               product_id BIGINT NOT NULL,
                               quantity INT NOT NULL,
                               PRIMARY KEY (order_id, product_id),
                               CONSTRAINT fk_order_product FOREIGN KEY (order_id) REFERENCES orders(id)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE,
                               CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE
);