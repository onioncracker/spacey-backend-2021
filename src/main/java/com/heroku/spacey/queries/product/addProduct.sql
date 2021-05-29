DO $$
    DECLARE material_Id INTEGER;
    DECLARE category_Id INTEGER;
    DECLARE product_Id INTEGER;
    BEGIN
        INSERT INTO categories(name)
            VALUES (?) RETURNING categories.id INTO category_Id;

        INSERT INTO materials(name)
            VALUES(?) RETURNING materials.id INTO material_Id;

        INSERT INTO material_to_products(materialid, productid)
            VALUES (material_Id, category_Id);

        INSERT INTO products(categoryid, name, dateaddproduct, sex, price, photo, description, discount, availability)
            VALUES (category_Id, ?, current_timestamp, ?, ?, ?, ?, ?, ?) RETURNING products.id INTO product_Id;

        INSERT INTO product_details(productid, color, sizeproduct, amount)
            VALUES (product_Id, ?, ?, ?);

        SELECT * FROM products WHERE products.id = product_Id;
END $$;