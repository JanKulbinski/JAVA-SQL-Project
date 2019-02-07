INSERT INTO Clients 
SELECT c.customer_id, c.first_name, c.last_name, a.address, c.email
FROM sakila.customer AS c
JOIN sakila.address AS a ON c.address_id = a.address_id ;

INSERT INTO Employees 
SELECT s.actor_id, s.first_name, s.last_name,FLOOR(RAND() * 8000)+ 2000,0,"clerk"
FROM sakila.actor AS s
Where s.actor_id < 15;

INSERT INTO Employees 
SELECT s.actor_id, s.first_name, s.last_name,FLOOR(RAND() * 8000)+ 2000,0,"storekeeper"
FROM sakila.actor AS s
Where s.actor_id >= 15 AND s.actor_id < 25;

INSERT INTO Employees(first_name,last_name,salary,commision,role)
VALUES ('Jan','Kowalski',10000,0,'manager');

INSERT INTO Players(first_name,last_name,nationality,club,role)
SELECT c.first_name, c2.last_name,co.country,CONCAT('FC ',ci.city),'defender'
FROM sakila.customer AS c
JOIN sakila.customer AS c2 ON c.customer_id = 10 + c2.customer_id
JOIN sakila.address AS a ON c.address_id = a.address_id
JOIN sakila.city AS ci ON ci.city_id = a.city_id
JOIN sakila.country AS co ON co.country_id = ci.country_id; 

INSERT INTO Players(first_name,last_name,nationality,club,role)
SELECT c.first_name, c2.last_name,co.country,CONCAT('FC ',ci.city),'midfielder'
FROM sakila.customer AS c
JOIN sakila.customer AS c2 ON c.customer_id = 34 + c2.customer_id
JOIN sakila.address AS a ON c.address_id = a.address_id
JOIN sakila.city AS ci ON ci.city_id = a.city_id
JOIN sakila.country AS co ON co.country_id = ci.country_id; 

INSERT INTO Players(first_name,last_name,nationality,club,role)
SELECT c.first_name, c2.last_name,co.country,CONCAT('FC ',ci.city),'striker'
FROM sakila.customer AS c
JOIN sakila.customer AS c2 ON c.customer_id = 23 + c2.customer_id
JOIN sakila.address AS a ON c.address_id = a.address_id
JOIN sakila.city AS ci ON ci.city_id = a.city_id
JOIN sakila.country AS co ON co.country_id = ci.country_id; 

INSERT INTO Players(first_name,last_name,nationality,club,role)
SELECT c.first_name, c2.last_name,co.country,CONCAT('FC ',ci.city),'goalkeeper'
FROM sakila.customer AS c
JOIN sakila.customer AS c2 ON c.customer_id = 87 + c2.customer_id
JOIN sakila.address AS a ON c.address_id = a.address_id
JOIN sakila.city AS ci ON ci.city_id = a.city_id
JOIN sakila.country AS co ON co.country_id = ci.country_id;

INSERT INTO Products(player_id,clothe_type,price)
SELECT player_id,'Shirt',FLOOR(RAND() * 200)+ 25
FROM Players; 
INSERT INTO Products(player_id,clothe_type,price)
SELECT player_id,'Shorts',FLOOR(RAND() * 200)+ 25
FROM Players;
INSERT INTO Products(player_id,clothe_type,price)
SELECT player_id,'Socks',FLOOR(RAND() * 200)+ 25
FROM Players;
INSERT INTO Products(player_id,clothe_type,price)
SELECT player_id,'Full',FLOOR(RAND() * 200)+ 25
FROM Players;

INSERT INTO Store
SELECT product_id,FLOOR(RAND() * 300) FROM Products;

INSERT INTO Orders (order_id,employee_id,product_id,client_id,amount)
SELECT player_id + 1,FLOOR(RAND() * 25) + 1,FLOOR(RAND() * 1000) + 1,FLOOR(RAND() * 599) + 1,FLOOR(RAND() * 20)+ 1
FROM Players;