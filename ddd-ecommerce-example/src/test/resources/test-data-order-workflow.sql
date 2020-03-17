TRUNCATE TABLE products;
TRUNCATE TABLE products_in_stock;

INSERT INTO products VALUES
    (1, 'P001', 'Prod 1', 'Prod 1 Desc', 1.00),
    (2, 'P002', 'Prod 2', 'Prod 2 Desc', 2.00);

INSERT INTO products_in_stock VALUES
    ('P001', 1),
    ('P002', 1000);