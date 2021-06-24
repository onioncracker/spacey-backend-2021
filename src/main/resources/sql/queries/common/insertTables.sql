INSERT INTO categories VALUES (1, 'shirts');
INSERT INTO categories VALUES (2, 'shoes');
INSERT INTO colors VALUES (1, 'yellow');
INSERT INTO colors VALUES (2, 'red');

INSERT INTO materials VALUES (1, 'cotton');
INSERT INTO materials VALUES (2, 'silicone');
INSERT INTO materials VALUES (3, 'leather');
INSERT INTO materials VALUES (4, 'test1');
INSERT INTO materials VALUES (5, 'test2');
INSERT INTO materials VALUES (6, 'test3');

INSERT INTO sizes VALUES (1, 'X');
INSERT INTO sizes VALUES (2, 'XL');
INSERT INTO sizes VALUES (3, 'XXL');
INSERT INTO sizes VALUES (4, 'XXL');

INSERT INTO user_status VALUES (1, 'UNACTIVATED');
INSERT INTO user_status VALUES (2, 'ACTIVATED');


UPDATE material_to_products SET materialid=6 WHERE productid = 3 AND materialid = 1