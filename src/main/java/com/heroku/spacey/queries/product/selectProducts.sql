SELECT * FROM product_details;
SELECT * FROM products;
SELECT * FROM categories;
SELECT * FROM materials;
SELECT * FROM material_to_products;

--Add a discount
UPDATE products p
SET discount = 100
WHERE p.id = 2;

--Cancel adding a product
DELETE FROM products p
USING product_details AS pd, categories AS ct,
    material_to_products AS mtp, materials as mt
WHERE (p.categoryid = ct.id) AND (p.id=pd.productid)
AND (mtp.productid=p.id)
AND (mtp.materialid=mt.id)
AND p.id = 2;

SELECT * FROM categories ORDER BY id;
--Edit product
UPDATE categories
SET name = ?
WHERE id = ?;

--Remove existing product
--UPDATE products p
--SET