# Employees
DELIMITER $$
CREATE TRIGGER salary_before_insert BEFORE INSERT ON Employees
FOR EACH ROW
BEGIN
	IF NEW.salary < 0 THEN
        signal sqlstate '45000' set message_text = 'MyTriggerError: Trying to insert a negative value ';
    END IF;
END; $$ 
DELIMITER ;

DELIMITER $$
CREATE TRIGGER salary_before_update BEFORE UPDATE ON Employees
FOR EACH ROW
BEGIN
	IF NEW.salary < 0 THEN
        signal sqlstate '45000' set message_text = 'MyTriggerError: Trying to insert a negative value ';
    END IF;
END; $$ 
DELIMITER;

DELIMITER $$
CREATE TRIGGER commision_before_insert BEFORE INSERT ON Employees
FOR EACH ROW
BEGIN
	IF NEW.commision < 0 THEN
        signal sqlstate '45000' set message_text = 'MyTriggerError: Trying to insert a negative value ';
    END IF;
END; $$ 
DELIMITER ;

DELIMITER $$
CREATE TRIGGER commision_before_update BEFORE UPDATE ON Employees
FOR EACH ROW
BEGIN
	IF NEW.commision < 0 THEN
        signal sqlstate '45000' set message_text = 'MyTriggerError: Trying to insert a negative value ';
    END IF;
END; $$ 
DELIMITER;

# Clients
DELIMITER $$
CREATE TRIGGER email_before_insert BEFORE INSERT ON Clients
FOR EACH ROW
BEGIN
	IF NEW.email NOT LIKE '%@%' THEN
        signal sqlstate '45000' set message_text = 'MyTriggerError: lack of @ ';
    END IF;
END; $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER email_before_update BEFORE UPDATE ON Clients
FOR EACH ROW
BEGIN
	IF NEW.email NOT LIKE '%@%' THEN
        signal sqlstate '45000' set message_text = 'MyTriggerError: lack of @ ';
    END IF;
END; $$ 
DELIMITER ;

# Orders
DELIMITER $$
CREATE TRIGGER amount_before_insert BEFORE INSERT ON Orders
FOR EACH ROW
BEGIN
	IF NEW.amount < 0 THEN
        signal sqlstate '45000' set message_text = 'MyTriggerError: Trying to insert a negative value ';
    END IF;
END; $$ 
DELIMITER ;

DELIMITER $$
CREATE TRIGGER amount_before_update BEFORE UPDATE ON Orders
FOR EACH ROW
BEGIN
	IF NEW.amount < 0 THEN
        signal sqlstate '45000' set message_text = 'MyTriggerError: Trying to insert a negative value ';
    END IF;
END; $$ 
DELIMITER ;

# Store
DELIMITER $$
CREATE TRIGGER number_of_products_before_insert BEFORE INSERT ON Store
FOR EACH ROW
BEGIN
	IF NEW.number_of_products < 0 THEN
        signal sqlstate '45000' set message_text = 'MyTriggerError: Trying to insert a negative value ';
    END IF;
END; $$ 
DELIMITER ;

DELIMITER $$
CREATE TRIGGER number_of_products_before_update BEFORE UPDATE ON Store
FOR EACH ROW
BEGIN
	IF NEW.number_of_products < 0 THEN
        signal sqlstate '45000' set message_text = 'MyTriggerError: Trying to insert a negative value ';
    END IF;
END; $$ 
DELIMITER ;

