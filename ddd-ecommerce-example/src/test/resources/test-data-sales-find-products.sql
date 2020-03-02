INSERT INTO categories VALUES
    (1, 'cat1', 'Cat 1'),
    (2, 'cat2', 'Cat 2');

INSERT INTO products VALUES
    (1, '11111111-1111-1111-1111-111111111111', 'Prod 1', 'Prod 1 Desc', 1.00),
    (2, '22222222-2222-2222-2222-222222222222', 'Prod 2', 'Prod 2 Desc', 2.00),
    (3, '33333333-3333-3333-3333-333333333333', 'Prod 3', 'Prod 3 Desc', 3.50);

INSERT INTO products_in_categories VALUES
    (1, 1),
    (2, 1),
    (3, 2);
