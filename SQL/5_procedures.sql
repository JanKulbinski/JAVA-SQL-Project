DELIMITER $$
CREATE PROCEDURE count_commision ()
BEGIN
UPDATE Employees AS e
JOIN v1 ON e.employee_id = v1.i
SET commision = v1.co
WHERE v1.i <> 0;
END
$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE reset_commision ()
BEGIN
UPDATE Employees AS e
SET commision = 0
WHERE e.employee_id <> 0;
END
$$
DELIMITER ;



