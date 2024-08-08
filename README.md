# Spring MVC, Thymeleaf, SQL, JPA (Hibernate) web application

## contents
0. [About](https://github.com/PavelSav1n/spring-final-project#0-about)
1. [SQL setup](https://github.com/PavelSav1n/spring-final-project#1-sql-setup)
2. [application.properties preparation](https://github.com/PavelSav1n/spring-final-project#2-applicationproperties-preparation)
3. [Other settings](https://github.com/PavelSav1n/spring-final-project#3-other-settings)

## 0. About

This application was developed as a final task of ITsJAVA Java Developer Spring course. The aim was to develop a web application using following features:
- Spring Web
- Model-View-Control pattern
- Thymeleaf
- SQL
- JPA (Hibernate)
- SOLID



## 1. SQL setup

1.1. This application requires MySQL server, or any other [RDBMS](https://en.wikipedia.org/wiki/Relational_database#RDBMS). How to install and setup MySQL server on Windows you can see [this](https://youtu.be/u96rVINbAUI) video. Here you can download [MySQL Installer 8.0.32](https://dev.mysql.com/get/Downloads/MySQLInstaller/mysql-installer-community-8.0.32.0.msi)

1.2. After you've finished with MySQL server setup, you will need to create a `schema` for tables where we will store all application data.
```SQL
CREATE DATABASE `spring_final_project`;
```
1.3. Create all following tables and just import this [sql dump file](https://github.com/PavelSav1n/spring-final-project/blob/master/src/main/resources/sql/spring-final-porject%20-%20Dump20240808.sql) into your DB. Or populate it with following data:

<details>
  
```SQL
/* roles table */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO	roles (name) VALUES ('admin'),('Customer'),('Manager'),('Accounter'),('Tester');
```
```SQL
/* users table */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `email` varchar(64) NOT NULL,
  `password` varchar(256) NOT NULL,
  `bdate` date DEFAULT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `role_idx` (`role_id`),
  CONSTRAINT `role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO users (id, name, email, password, bdate, role_id) VALUES
(1, 'Administrator', 'admin@mail.com', 'admin123', '1993-01-01', 1),
(2, 'John The Customer', 'customer@mail.com', 'cust123', '1983-01-01', 2),
(3, 'Will The Manager', 'testEmail@mail.com', 'qwerty', '2023-08-08', 7),
(4, 'Mike The Administrator', 'asdf@32.com', '123123', '2023-08-02', 1),
(5, 'Loui The Manager', 'loui@mail.com', 'helloworld', '2023-08-03', 7),
(6, 'Li Hong The Customer', 'li@hotmail.com', '123', '2023-08-02', 2),
(19, 'Bill The Tester', 'Test@test.com', '123', '2024-07-26', 9);
```
```SQL
/* orders table */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint DEFAULT NULL REFERENCES users(id),
  `cost` double DEFAULT NULL,
  `temp` boolean DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO orders (id, order_date, user_id, cost, temp) VALUES
(1, '2024-08-02 12:26:01', 1, 40.0, false),
(2, '2024-08-02 12:26:01', 1, 81208.0, false),
(3, '2024-08-02 12:26:01', 4, 2242.0, false),
(4, '2024-08-02 12:26:01', 2, 15570.0, false),
(5, '2024-08-01 00:00:00', 2, 96.0, false),
(6, '2024-08-06 16:34:00', 2, 1004.0, false),
(9, '2024-08-07 15:43:08', 2, 54.0, false),
(10, '2024-08-07 16:04:28', 6, 50.0, true),
(11, '2024-08-07 16:05:29', 2, 502.0, false),
(12, '2024-08-08 13:21:00', 2, 652.0, false);
```
```SQL
/* categories (of products) */

DROP TABLE IF EXISTS `categories`;

CREATE TABLE `categories` (
`id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(256) NOT NULL UNIQUE
);

INSERT INTO `categories` (name) VALUES ('Fruit'), ('Sport'), ('Medicine'), ('Clothes'), ('Vegetable');
```
```SQL
/* products table */

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
`id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(256) NOT NULL,
`price` DOUBLE,
`category_id` BIGINT REFERENCES categories(id),
`image_path` VARCHAR(256),
`details` VARCHAR(256)
);

INSERT INTO products (name, price, category_id, image_path, details) VALUES
    ('Apple', 2.00, 1, 'imagepath/apple.jpg', 'Apple details'),
    ('Ball', 50.00, 2, 'imagepath/ball.jpg', 'Ball details'),
    ('Medkit', 500.00, 3, 'imagepath/medkit.jpg', 'mMdkit details'),
    ('Banana', 2.00, 1, 'imagepath/banana.jpg', 'Banana details'),
    ('Peach', 4.00,  1, 'imagepath/peach.jpg', 'Peach details'),
    ('Potato', 4.00,  5, 'imagepath/potato.jpg', 'Potato details'),
    ('Carrot', 3.00,  5, 'imagepath/carrot.jpg', 'Carrot details'),
    ('Tennis racket', 400.00,  2, 'imagepath/tennis_racket.jpg', 'Tennis racket details');
```
```SQL
/* stock table */

DROP TABLE IF EXISTS `stock`;
CREATE TABLE stock (
`id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
`product_id` BIGINT NOT NULL REFERENCES products(id),
`amount` INT
);

INSERT INTO stock (product_id, amount) VALUES 
	(1, 100),
	(2, 50),
	(3, 10),
	(4, 22),
	(5, 149),
	(6, 200),
	(7, 300),
	(8, 24);    
```
```SQL
/* order_details */
DROP TABLE IF EXISTS `order_details`;
CREATE TABLE `order_details` (
	`id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `order_id` BIGINT NOT NULL REFERENCES orders(id),
    `product_id` BIGINT NOT NULL REFERENCES products(id),
    `amount` INT NOT NULL,
    `price` DOUBLE
);
INSERT INTO order_details (id, order_id, product_id, amount, price) VALUES
(1, 1, 1, 3, 6.0),
(2, 1, 4, 4, 8.0),
(3, 2, 1, 3, 6.0),
(4, 2, 2, 33, 1650.0),
(5, 2, 3, 34, 17000.0),
(6, 1, 4, 13, 26.0),
(7, 3, 5, 233, 932.0),
(8, 3, 1, 332, 664.0),
(9, 4, 3, 31, 15500.0),
(10, 4, 1, 35, 70.0),
(12, 2, 3, 122, 61000.0),
(13, 3, 2, 12, 600.0),
(15, 3, 1, 23, 46.0),
(16, 2, 6, 776, 1552.0),
(29, 5, 2, 1, 50.0),
(31, 5, 5, 10, 40.0),
(33, 6, 3, 2, 1000.0),
(39, 6, 4, 1, 2.0),
(42, 9, 4, 1, 2.0),
(43, 10, 2, 1, 50.0),
(44, 11, 4, 1, 2.0),
(45, 11, 3, 1, 500.0),
(46, 5, 1, 1, 2.0),
(48, 5, 6, 2, 4.0),
(49, 6, 1, 1, 2.0),
(50, 9, 2, 1, 50.0),
(57, 9, 1, 1, 2.0),
(58, 12, 1, 1, 2.0),
(59, 12, 2, 13, 650.0);
```
</details>

## 2. application.properties preparation

Once you've finished SQL setup, it's time to set up [application.properties](https://github.com/PavelSav1n/spring-final-project/blob/master/src/main/resources/application.properties)

`db.url` -- is for MySQL server address. If localhost, then keep default settings.

`db.login` and `db.password` -- is for SQL database login & password respectively.

```C#
db.url=jdbc:MySql://localhost:3306/spring_final_project?serverTimezone=UTC
db.login=yourLoginHere
db.password=yourPasswordHere
```

## 3. Other settings

For application to run properly, you must open default port `3306` in your firewall. At the first start of application Windows will ask whether permit the access for application to port `3306`

:arrow_up_small:[Back to contents](https://github.com/PavelSav1n/spring-final-project#contents)
