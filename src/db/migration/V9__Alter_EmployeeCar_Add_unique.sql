DELETE FROM Timeframe;
DELETE FROM EmployeeCar;
DELETE FROM Employee;
DELETE FROM Car;

ALTER TABLE EmployeeCar ADD UNIQUE(EmployeeID, CarID);