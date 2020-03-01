-- ------ SALES ------

CREATE TABLE categories (
    id INT NOT NULL PRIMARY KEY,
    uri VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(20) NOT NULL
);

CREATE TABLE products (
    id INT NOT NULL PRIMARY KEY,
    code CHAR(36) NOT NULL UNIQUE,
    title VARCHAR(20) NOT NULL,
    description VARCHAR(50) NOT NULL DEFAULT(''),
    price DECIMAL(10,2)
);

CREATE TABLE products_in_categories (
    product_id INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (product_id, category_id)
);

-- ------ WAREHOUSE ------

CREATE TABLE products_in_stock (
    product_id INT NOT NULL PRIMARY KEY,
    amount INT NOT NULL DEFAULT(0)
);