INSERT INTO categories VALUES
    (1, 'cat1', 'Cat 1'),
    (2, 'cat2', 'Cat 2');

INSERT INTO products VALUES
    (1, '6ef8c68e-62cf-4697-a4f9-1950181097bb', 'Prod 1', 'Prod 1 Desc', 1.00),
    (2, 'cc4608a7-a4cd-44bb-aae5-2fa63a047f67', 'Prod 2', 'Prod 2 Desc', 2.00),
    (3, '179ee435-34c4-4d9d-842d-5f168fae363e', 'Prod 3', 'Prod 3 Desc', 3.50);

INSERT INTO products_in_categories VALUES
    (1, 1),
    (2, 1),
    (3, 2);
