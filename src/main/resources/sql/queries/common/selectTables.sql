SELECT * FROM sizes;
SELECT * FROM colors;
SELECT * FROM products;
SELECT * FROM categories;
SELECT * FROM materials;
SELECT * FROM material_to_products;
SELECT * FROM size_to_products;
SELECT * FROM users;
SELECT * FROM verification_token;
SELECT * FROM user_status;



SELECT tokenId, confirmationToken, date FROM verification_token
INNER JOIN users u on verification_token.tokenid = u.tokenid;