CREATE TABLE login_info
(
    LoginId SERIAL PRIMARY KEY,
    Email CHARACTER VARYING(45),
    Pass CHARACTER VARYING(45),
    UserRole CHARACTER VARYING(20),
    FirstName CHARACTER VARYING(20),
    LastName CHARACTER VARYING(30),
    Status CHARACTER VARYING(20),
    PhoneNumber CHARACTER VARYING(13)
);
CREATE TABLE user_roles
(
    RoleId SERIAL PRIMARY KEY,
    LoginId INTEGER REFERENCES login_info,
    RoleName CHARACTER VARYING(45)
);
CREATE TABLE user_details
(
    UserId SERIAL PRIMARY KEY,
    LoginId INTEGER REFERENCES login_info,
    City CHARACTER VARYING(45),
    Street CHARACTER VARYING(45),
    House CHARACTER VARYING(45),
    Apartment CHARACTER VARYING(45),
    DateOfBirth DATE NULL,
    Sex CHARACTER VARYING(20)
);
CREATE TABLE product_carts
(
    CartId SERIAL PRIMARY KEY,
    UserId INTEGER REFERENCES user_details,
    OverallPrice DECIMAL
);
CREATE TABLE categories
(
    CategoryId SERIAL PRIMARY KEY,
    NameCategory CHARACTER VARYING(30)
);
CREATE TABLE products
(
    ProductId SERIAL PRIMARY KEY,
    CategoryId INTEGER REFERENCES categories ON DELETE CASCADE,
    ProductName CHARACTER VARYING(50),
    CreatedDate TIMESTAMP,
    ProductSex CHARACTER VARYING(20),
    Price DECIMAL,
    Photo CHARACTER VARYING(512),
    Description CHARACTER VARYING(512) ,
    Discount NUMERIC,
    IsAvailable BOOL,
    IsOnAuction BOOL
);
CREATE TABLE product_to_carts
(
    CartId INTEGER REFERENCES product_carts ON DELETE CASCADE,
    ProductId INTEGER REFERENCES products ON DELETE CASCADE,
    Amount INTEGER,
    Sum DECIMAL,
    PRIMARY KEY (CartId, ProductId)
);
CREATE TABLE materials
(
    MaterialId SERIAL PRIMARY KEY,
    NameMaterial CHARACTER VARYING(30)
);
CREATE TABLE material_to_products
(
    MaterialID INTEGER REFERENCES materials ON DELETE CASCADE,
    ProductId INTEGER REFERENCES products ON DELETE CASCADE,
    PRIMARY KEY(MaterialID, ProductId)
);
CREATE TABLE product_to_compare
(
    UserID INTEGER REFERENCES user_details,
    ProductId INTEGER REFERENCES products,
    DateCompare TIMESTAMP,
    PRIMARY KEY(UserID, ProductId)
);
CREATE TABLE product_details
(
    DetailsId SERIAL PRIMARY KEY,
    ProductId INTEGER REFERENCES products ON DELETE CASCADE,
    Color CHARACTER VARYING(30),
    SizeProduct CHARACTER VARYING(5),
    Amount INTEGER
);
CREATE TABLE orders
(
    OrderId SERIAL PRIMARY KEY,
    UserId INTEGER REFERENCES user_details,
    ProductId INTEGER REFERENCES products,
    Status CHARACTER VARYING (20),
    Datetime TIMESTAMP,
    OverallPrice DECIMAL,
    CommentOrder CHARACTER VARYING (250) NULL
);
CREATE TABLE product_orders
(
    OrderId INTEGER REFERENCES orders,
    ProductId INTEGER REFERENCES products,
    Amount INTEGER,
    Sum DECIMAL,
    PRIMARY KEY (OrderId, ProductId)
);
CREATE TABLE notifications
(
    NotificationId SERIAL PRIMARY KEY,
    NotificationText CHARACTER VARYING (512),
    NotificationDate TIMESTAMP,
    Status CHARACTER VARYING (50)
);
CREATE TABLE notification_to_users
(
    UserId INTEGER REFERENCES user_details,
    NotificationId INTEGER REFERENCES notifications,
    PRIMARY KEY (UserId, NotificationId)
);
CREATE TABLE auctions
(
    AuctionId SERIAL PRIMARY KEY,
    UserId INTEGER REFERENCES user_details,
    ProductId INTEGER REFERENCES products,
    AuctionName CHARACTER VARYING(50),
    Price DECIMAL,
    AuctionDate TIMESTAMP,
    Quantity INTEGER,
    Status CHARACTER VARYING(45)
);
CREATE TABLE delivery_info
(
    DeliveryId SERIAL PRIMARY KEY,
    UserId INTEGER REFERENCES user_details,
    OrderId INTEGER REFERENCES orders,
    Name CHARACTER VARYING(45),
    PhoneNumber CHARACTER VARYING (13),
    City CHARACTER VARYING(45),
    Street CHARACTER VARYING(45),
    House CHARACTER VARYING(45),
    Apartment CHARACTER VARYING(45)
);