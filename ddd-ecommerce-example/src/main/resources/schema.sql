CREATE TABLE categories (
    id CHAR(36) NOT NULL PRIMARY KEY,
    title VARCHAR(100) NOT NULL
);

CREATE TABLE products (
    id CHAR(36) NOT NULL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2)
);

CREATE TABLE products_in_categories (
    product_id CHAR(36) NOT NULL,
    category_id CHAR(36) NOT NULL,
    PRIMARY KEY (product_id, category_id)
);