CREATE TABLE login_infos
(
    Id SERIAL PRIMARY KEY,
    Email CHARACTER VARYING(45),
    Pass CHARACTER VARYING(45),
    UserRole CHARACTER VARYING(20),
    FirstName CHARACTER VARYING(20),
    LastName CHARACTER VARYING(30),
    Status CHARACTER VARYING(20) NULL,
    PhoneNumber CHARACTER VARYING(50) NULL
);

CREATE TABLE user_details
(
    UserId SERIAL PRIMARY KEY,
    LoginId INTEGER REFERENCES login_infos,
    City CHARACTER VARYING(45),
    Street CHARACTER VARYING(45),
    House CHARACTER VARYING(45),
    Apartment CHARACTER VARYING(45) NULL,
    DateOfBirth DATE NULL,
    Sex CHARACTER VARYING(20) NOT NULL
);


CREATE TABLE product_carts
(
    Id SERIAL PRIMARY KEY,
    UserId INTEGER REFERENCES user_details,
    OverallPrice MONEY
);

CREATE TABLE categories
(
    Id SERIAL PRIMARY KEY,
    Name CHARACTER VARYING(30)
);

CREATE TABLE products
(
    Id SERIAL PRIMARY KEY,
    CategoryId INTEGER REFERENCES categories(id) ON DELETE CASCADE,
    Name CHARACTER VARYING(50),
    CreatedDate TIMESTAMP,
    ProductSex CHARACTER VARYING(20),
    Price DECIMAL,
    Photo CHARACTER VARYING(50),
    Description CHARACTER VARYING(512),
    Discount NUMERIC,
    IsAvailable BOOL
);

CREATE TABLE product_to_carts
(
    CartId INTEGER REFERENCES product_carts ON DELETE CASCADE,
    ProductId INTEGER REFERENCES products(Id) ON DELETE CASCADE,
    Amount INTEGER,
    Sum MONEY,
    PRIMARY KEY (CartId, ProductId)
);

CREATE TABLE materials
(
    Id SERIAL PRIMARY KEY,
    Name CHARACTER VARYING(30)
);

CREATE TABLE material_to_products
(
    MaterialId INTEGER REFERENCES materials(id) ON DELETE CASCADE,
    ProductId INTEGER REFERENCES products(id) ON DELETE CASCADE,
    PRIMARY KEY(MaterialId, ProductId)
);

CREATE TABLE product_to_compares
(
    UserID INTEGER REFERENCES user_details,
    ProductId INTEGER REFERENCES products,
    PRIMARY KEY(UserID, ProductId)
);


CREATE TABLE product_details
(
    Id SERIAL PRIMARY KEY,
    ProductId INTEGER REFERENCES products(id) ON DELETE CASCADE,
    Color CHARACTER VARYING(30),
    SizeProduct CHARACTER VARYING(5),
    Amount INTEGER
);


CREATE TABLE orders
(
    Id SERIAL PRIMARY KEY,
    UserId INTEGER REFERENCES user_details,
    ProductId INTEGER REFERENCES products,
    Status CHARACTER VARYING (20),
    Datetime TIMESTAMP,
    Address CHARACTER VARYING (120),
    PhoneNumber CHARACTER VARYING (13),
    OverallPrice MONEY,
    CommentOrder CHARACTER VARYING (250) NULL
);


CREATE TABLE product_orders
(
    OrderId INTEGER REFERENCES orders,
    ProductId INTEGER REFERENCES products,
    Amount INTEGER,
    Sum MONEY,
    PRIMARY KEY (OrderId, ProductId)
);