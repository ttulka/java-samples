-- ------ SALES ------

INSERT INTO categories VALUES
    (1, 'books', 'books'),
    (2, 'games-toys', 'games and toys'),
    (3, 'others', 'others');

INSERT INTO products VALUES
    (1, '6ef8c68e-62cf-4697-a4f9-1950181097bb', 'Domain-Driven Design', 'by Eric Evans', 45.00),
    (2, 'cc4608a7-a4cd-44bb-aae5-2fa63a047f67', 'Object Thinking', 'by David West', 35.00),
    (3, '179ee435-34c4-4d9d-842d-5f168fae363e', 'Release It!', 'by Michael Nygard', 32.50),
    (4, '4f61a8dd-b8ad-4518-bbe1-a7171ef01803', 'Chess', 'Deluxe edition of the classic game.', 3.20),
    (5, '7d234dca-c89b-4661-b899-6a2b87647462', 'Domino', 'In black or white.', 1.50),
    (6, 'ebc81f9e-f6cd-4b8a-81b7-b3c54c8e7009', 'Klein bottle', 'Two-dimensional manifold made from glass.', 25.00);

INSERT INTO products_in_categories VALUES
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 2),
    (5, 2),
    (6, 3);

-- ------ WAREHOUSE ------

INSERT INTO products_in_stock VALUES
    (1, 5),
    (2, 0),
    (3, 13),
    (4, 55),
    (5, 102),
    (6, 1);