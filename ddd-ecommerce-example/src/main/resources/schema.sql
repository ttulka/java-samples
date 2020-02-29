CREATE TABLE categories (
    id VARCHAR(20) NOT NULL PRIMARY KEY,
    title VARCHAR(20) NOT NULL
);

CREATE TABLE products (
    id CHAR(36) NOT NULL PRIMARY KEY,
    title VARCHAR(20) NOT NULL,
    description VARCHAR(50) NOT NULL DEFAULT(''),
    price DECIMAL(10,2)
);

CREATE TABLE products_in_categories (
    product_id CHAR(36) NOT NULL,
    category_id CHAR(36) NOT NULL,
    PRIMARY KEY (product_id, category_id)
);