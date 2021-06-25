SELECT p.*, c.*, m.*, s.*, clrs.*
FROM products p
INNER JOIN material_to_products mtp on p.productId = mtp.productId
INNER JOIN size_to_products stp on p.productid = stp.productid
INNER JOIN materials m on mtp.materialId = m.materialId
INNER JOIN categories c on p.categoryId = c.categoryId
INNER JOIN sizes s on stp.sizeid = s.sizeid
INNER JOIN colors clrs on clrs.colorid = p.colorid
WHERE p.productId = 7
