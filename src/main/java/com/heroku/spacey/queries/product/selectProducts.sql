SELECT * FROM product_details;
SELECT * FROM products;
SELECT * FROM categories;
SELECT * FROM materials;
SELECT * FROM material_to_products;

--Cancel adding a product
DELETE FROM products p
USING product_details AS pd, categories AS ct,
    material_to_products AS mtp, materials as mt
WHERE (p.categoryid = ct.id) AND (p.id=pd.productid)
AND (mtp.productid=p.id)
AND (mtp.materialid=mt.id)
AND p.id = 2;

SELECT p.*, c.*, m.*, pd.*
FROM products p
INNER JOIN material_to_products mtp on p.id = mtp.productid
INNER JOIN materials m on mtp.materialid = m.id
INNER JOIN categories c on p.categoryid = c.id
INNER JOIN product_details pd on p.id = pd.productid
WHERE p.id = 13;

SELECT p.id, m.id, c.id, pd.id
FROM products p
         INNER JOIN material_to_products mtp on p.id = mtp.productid
         INNER JOIN materials m on mtp.materialid = m.id
         INNER JOIN categories c on p.categoryid = c.id
         INNER JOIN product_details pd on p.id = pd.productid
WHERE p.id = 10