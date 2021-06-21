CREATE TABLE roles
(
    RoleId BIGSERIAL PRIMARY KEY,
    RoleName CHARACTER VARYING (30)
);


CREATE TABLE user_status
(
    StatusId BIGSERIAL PRIMARY KEY,
    StatusName CHARACTER VARYING (30)
);

CREATE TABLE verification_token
(
    TokenId BIGSERIAL PRIMARY KEY,
    ConfirmationToken VARCHAR(255),
    Date Date
);

CREATE TABLE users
(
    UserId BIGSERIAL PRIMARY KEY,
    RoleId INTEGER REFERENCES roles,
    StatusId INTEGER REFERENCES user_status,
    TokenId INTEGER REFERENCES verification_token,
    Email CHARACTER VARYING(45),
    Pass CHARACTER VARYING(256),
    FirstName CHARACTER VARYING(20),
    LastName CHARACTER VARYING(30),
    City CHARACTER VARYING(45),
    Street CHARACTER VARYING(45),
    House CHARACTER VARYING(45),
    Appartment CHARACTER VARYING(45),
    DateOfBirth DATE,
    Sex CHARACTER VARYING(20),
    PhoneNumber CHARACTER VARYING(13)
);


CREATE TABLE product_carts
(
    CartId BIGSERIAL PRIMARY KEY,
    UserId INTEGER REFERENCES users,
    OverallPrice DECIMAL
);


CREATE TABLE categories
(
    CategoryId BIGSERIAL PRIMARY KEY,
    NameCategory CHARACTER VARYING(30)
);


CREATE TABLE colors
(
    ColorId BIGSERIAL PRIMARY KEY,
    Color CHARACTER VARYING(45)
);


CREATE TABLE products
(
    ProductId BIGSERIAL PRIMARY KEY,
    CategoryId INTEGER REFERENCES categories ON DELETE CASCADE,
    ColorId INTEGER REFERENCES colors ON DELETE CASCADE,
    ProductName CHARACTER VARYING(50),
    CreatedDate TIMESTAMP,
    ProductSex CHARACTER VARYING(20),
    Price DECIMAL,
    Photo CHARACTER VARYING(512),
    Description CHARACTER VARYING(512),
    Discount NUMERIC,
    IsAvailable BOOL
);


CREATE TABLE sizes
(
    SizeId BIGSERIAL PRIMARY KEY,
    SizeName CHARACTER VARYING (20)
);


CREATE TABLE size_to_products
(
    SizeID INTEGER REFERENCES sizes ON DELETE CASCADE,
    ProductId INTEGER REFERENCES products ON DELETE CASCADE,
    Quantity INTEGER,
    PRIMARY KEY(SizeID, ProductId)
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
    MaterialId BIGSERIAL PRIMARY KEY,
    NameMaterial CHARACTER VARYING (30)
);


CREATE TABLE material_to_products
(
    MaterialID INTEGER REFERENCES materials ON DELETE CASCADE,
    ProductId INTEGER REFERENCES products ON DELETE CASCADE,
    PRIMARY KEY(MaterialID, ProductId)
);


CREATE TABLE product_to_compare
(
    UserID INTEGER REFERENCES users ON DELETE CASCADE,
    ProductId INTEGER REFERENCES products ON DELETE CASCADE,
    DateCompare TIMESTAMP,
    PRIMARY KEY(UserID, ProductId)
);


CREATE TABLE order_status
(
    OrderStatusId BIGSERIAL PRIMARY KEY,
    Status CHARACTER VARYING (30)
);


CREATE TABLE orders
(
    OrderId BIGSERIAL PRIMARY KEY,
    OrderStatusId INTEGER REFERENCES order_status,
    UserId INTEGER REFERENCES users,
    OrdenerName CHARACTER VARYING(45),
    OrdenerSurname CHARACTER VARYING(45),
    PhoneNumber CHARACTER VARYING (13),
    City CHARACTER VARYING(45),
    Street CHARACTER VARYING(45),
    House CHARACTER VARYING(45),
    Appartment CHARACTER VARYING(45),
    Datetime TIMESTAMP,
    OverallPrice DECIMAL,
    CommentOrder CHARACTER VARYING (250)
);


CREATE TABLE product_to_orders
(
    OrderId INTEGER REFERENCES orders ON DELETE CASCADE,
    ProductId INTEGER REFERENCES products ON DELETE CASCADE,
    Amount INTEGER,
    Sum DECIMAL,
    PRIMARY KEY (OrderId, ProductId)
);


CREATE TABLE notifications
(
    NotificationId BIGSERIAL PRIMARY KEY,
    NotificationText CHARACTER VARYING (512),
    NotificationDate TIMESTAMP,
    Status CHARACTER VARYING (50)
);


CREATE TABLE notification_to_users
(
    UserId INTEGER REFERENCES users ON DELETE CASCADE,
    NotificationId INTEGER REFERENCES notifications ON DELETE CASCADE,
    PRIMARY KEY (UserId, NotificationId)
);


CREATE TABLE auctions
(
    AuctionId BIGSERIAL PRIMARY KEY,
    UserId INTEGER REFERENCES users,
    AuctionName CHARACTER VARYING(50),
    Price DECIMAL,
    AuctionDate TIMESTAMP,
    Quantity INTEGER,
    Status CHARACTER VARYING(45)
);


CREATE TABLE product_to_auctions
(
    ProductId INTEGER REFERENCES products,
    AuctionId INTEGER REFERENCES auctions,
    PRIMARY KEY (ProductId, AuctionId)
);