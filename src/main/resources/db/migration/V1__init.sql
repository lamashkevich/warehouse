CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    name VARCHAR(256) NOT NULL
);

CREATE TABLE prices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    price_value DECIMAL(19, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id)
);