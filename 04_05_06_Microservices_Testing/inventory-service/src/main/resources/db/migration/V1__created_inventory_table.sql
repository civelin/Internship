CREATE TABLE inventory
(
    id        BIGINT NOT NULL AUTO_INCREMENT,
    product_id    BIGINT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);