SELECT p.*, c.*, m.*, s.*, clrs.*
FROM products p
INNER JOIN material_to_products mtp on p.productId = mtp.productId
INNER JOIN size_to_products stp on p.productid = stp.productid
INNER JOIN materials m on mtp.materialId = m.materialId
INNER JOIN categories c on p.categoryId = c.categoryId
INNER JOIN sizes s on stp.sizeid = s.sizeid
INNER JOIN colors clrs on clrs.colorid = p.colorid
WHERE p.productId = 10

SELECT p.*, c.categoryId category_id, c.nameCategory category_name,
m.materialId material_id, m.nameMaterial material_name,
s.sizeId size_id, s.sizeName size_name,
clrs.colorId color_id, clrs.color color
FROM products p
INNER JOIN material_to_products mtp on p.productId = mtp.productId
INNER JOIN size_to_products stp on p.productId = stp.productId
INNER JOIN materials m on mtp.materialId = m.materialId
INNER JOIN categories c on p.categoryId = c.categoryId
INNER JOIN sizes s on stp.sizeId = s.sizeId
INNER JOIN colors clrs on clrs.colorId = p.colorId
WHERE p.productId = 10