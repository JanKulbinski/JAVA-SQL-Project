CREATE VIEW v1 AS
SELECT employee_id AS i, SUM(0.03*amount*price) AS co
FROM Orders AS o
JOIN Products AS p ON p.product_id = o.product_id 
GROUP BY employee_id;

CREATE VIEW clients_for_clerks AS
SELECT client_id,first_name,last_name
FROM Clients;

CREATE VIEW employees_for_manager AS 
SELECT employee_id,first_name,last_name,commision
FROM Employees;

