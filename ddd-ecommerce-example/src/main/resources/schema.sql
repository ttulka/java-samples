-- ------ SALES ------

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT NOT NULL PRIMARY KEY,
    uri VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    id BIGINT NOT NULL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    title VARCHAR(20) NOT NULL,
    description VARCHAR(50) NOT NULL DEFAULT(''),
    price DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS products_in_categories (
    product_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, category_id)
);

-- ------ SHIPPING ------

CREATE TABLE IF NOT EXISTS deliveries (
    id BIGINT NOT NULL PRIMARY KEY,
    order_id BIGINT NOT NULL UNIQUE,
    person VARCHAR(50) NOT NULL,
    place VARCHAR(100) NOT NULL,
    shipped BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS delivery_items (
    product_code VARCHAR(50) NOT NULL,
    quantity INT NOT NULL DEFAULT(0),
    delivery_id BIGINT NOT NULL
);
CREATE INDEX IF NOT EXISTS delivery_id_idx ON delivery_items(delivery_id);

-- ------ WAREHOUSE ------

CREATE TABLE IF NOT EXISTS products_in_stock (
    product_code VARCHAR(50) NOT NULL PRIMARY KEY,
    amount INT NOT NULL DEFAULT(0)
);