DELETE FROM EmployeeCar;
DELETE FROM Employee;
DELETE FROM Car;
DELETE FROM Timeframe;

ALTER TABLE EmployeeCar ADD UNIQUE(EmployeeID, CarID);