
DELIMITER $$
CREATE FUNCTION hypest_product ()
RETURNS float(6,2)
DETERMINISTIC
BEGIN 
 DECLARE priceH float(6,2);
 SELECT MAX(price) INTO priceH FROM Products;
 RETURN dist;
END;

$$
DELIMITER ;