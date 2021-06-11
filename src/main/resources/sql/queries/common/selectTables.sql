SELECT * FROM sizes;
SELECT * FROM colors;
SELECT * FROM products;
SELECT * FROM categories;
SELECT * FROM materials;
SELECT * FROM material_to_products;
SELECT * FROM size_to_products;

INSERT INTO categories VALUES (1, 'shoes');
INSERT INTO colors VALUES (1, 'red');

INSERT INTO materials VALUES (1, 'cotton');
INSERT INTO materials VALUES (2, 'silicone');
INSERT INTO materials VALUES (3, 'leather');

INSERT INTO sizes VALUES (1, 'X');
INSERT INTO sizes VALUES (2, 'XL');
INSERT INTO sizes VALUES (3, 'XXL');