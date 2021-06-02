SELECT p.*, c.*, m.*, pd.*
FROM products p
         INNER JOIN material_to_products mtp on p.id = mtp.productid
         INNER JOIN materials m on mtp.materialid = m.id
         INNER JOIN categories c on p.categoryid = c.id
         INNER JOIN product_details pd on p.id = pd.productid
WHERE p.id = 62;


SELECT p.id, m.id, c.id, pd.id
FROM products p
         INNER JOIN material_to_products mtp on p.id = mtp.productid
         INNER JOIN materials m on mtp.materialid = m.id
         INNER JOIN categories c on p.categoryid = c.id
         INNER JOIN product_details pd on p.id = pd.productid
WHERE p.id = 6;


SELECT p.*, c.id category_id, c.name category_name, m.id material_id, m.name material_name,
       pd.id pd_id, pd.productid pd_product_id, pd.color pd_color, pd.sizeproduct pd_size, pd.amount pd_amount
FROM products p
         INNER JOIN material_to_products mtp on p.id = mtp.productid
         INNER JOIN materials m on mtp.materialid = m.id
         INNER JOIN categories c on p.categoryid = c.id
         INNER JOIN product_details pd on p.id = pd.productid
WHERE p.id = 7;

--Cancel adding a product
DELETE FROM products p
    USING product_details AS pd, categories AS ct,
        material_to_products AS mtp, materials as mt
WHERE (p.categoryid = ct.id) AND (p.id=pd.productid)
  AND (mtp.productid=p.id)
  AND (mtp.materialid=mt.id)
  AND p.id = 2;