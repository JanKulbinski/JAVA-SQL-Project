CREATE DATABASE IF NOT EXISTS `Football-Kits-Shop`;

CREATE USER `clerk`@`localhost`;
SET PASSWORD FOR `clerk`@`localhost` = PASSWORD('clerkP');

CREATE USER `storekeeper`@`localhost`;
SET PASSWORD FOR `storekeeper`@`localhost` = PASSWORD('storekeeperP');

CREATE USER `manager`@`localhost`;
SET PASSWORD FOR `manager`@`localhost` = PASSWORD('managerP');

CREATE TABLE Employees(
	employee_id smallint NOT NULL AUTO_INCREMENT,
    first_name varchar(45) NOT NULL,
    last_name varchar(45) NOT NULL,
    salary float(9,2) NOT NULL,
    commision float(4,2) DEFAULT 0,
    role enum('manager','clerk','storekeeper'),
    PRIMARY KEY (employee_id)
);


CREATE TABLE Clients(
	client_id smallint NOT NULL AUTO_INCREMENT,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    adress varchar(50) NOT NULL,
	email varchar(50) NOT NULL,
    PRIMARY KEY (client_id)
);

CREATE TABLE Players(
	player_id smallint NOT NULL AUTO_INCREMENT,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    nationality varchar(50) NOT NULL,
	club varchar(50) NOT NULL,
    role enum('goalkeeper','defender','midfielder','striker'),
    PRIMARY KEY (player_id)
);

CREATE TABLE Products(
	product_id smallint NOT NULL AUTO_INCREMENT,
	player_id smallint NOT NULL,
    clothe_type enum('shirt','shorts','socks','full'),
    price float(6,2),
    PRIMARY KEY (product_id),
	FOREIGN KEY (player_id) REFERENCES Players(player_id)
);

CREATE TABLE Store(
	product_id smallint(5) NOT NULL,
	number_of_products smallint(5) NOT NULL,
	FOREIGN KEY(product_id) REFERENCES Products(product_id)
);

CREATE TABLE Orders(
	order_id smallint(5) NOT NULL AUTO_INCREMENT,
	employee_id smallint(5) NOT NULL,
	product_id smallint(5) NOT NULL,
	client_id smallint(5) NOT NULL,
    order_date datetime DEFAULT NOW(),
    amount smallint(5) NOT NULL,
    PRIMARY KEY (order_id),
    FOREIGN KEY(employee_id) REFERENCES Employees(employee_id),
    FOREIGN KEY(product_id) REFERENCES Products(product_id),
    FOREIGN KEY(client_id) REFERENCES Clients(client_id)
);

GRANT SELECT ON mysql.proc TO 'manager'@'localhost';
GRANT ALL PRIVILEGES ON `Football-Kits-Shop`.* TO `manager`@`localhost`;
GRANT Select,Insert,Update ON `Football-Kits-Shop`.`Store` TO `storekeeper`@`localhost`;
GRANT Select,Insert,Update ON `Football-Kits-Shop`.`Orders` TO `clerk`@`localhost`;
GRANT Select,Insert,Update ON `Football-Kits-Shop`.`Players` TO `clerk`@`localhost`;
GRANT Select,Insert,Update ON `Football-Kits-Shop`.`Products` TO `clerk`@`localhost`;
GRANT Select,Insert,Update ON `Football-Kits-Shop`.`Store` TO `clerk`@`localhost`;
FLUSH PRIVILEGES;



