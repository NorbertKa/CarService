INSERT INTO Employee(Name, Surname)
VALUES ('Norberts', 'Kakste')

INSERT INTO Employee(Name, Surname)
VALUES ('Roberts', 'Berzins')

INSERT INTO Employee(Name, Surname)
VALUES ('Ivars', 'Kasparsons')

INSERT INTO Employee(Name, Surname)
VALUES ('Ieva', 'Ozolina')

INSERT INTO Employee(Name, Surname)
VALUES ('Peteris', 'Kalnins')

INSERT INTO Car(Manufacturer, Model, RegNumber)
VALUES ('Audi', 'AS6', 'GR-1056')

INSERT INTO Car(Manufacturer, Model, RegNumber)
VALUES ('Audi', 'AS6', 'TR-1142')

INSERT INTO Car(Manufacturer, Model, RegNumber)
VALUES ('Ford', 'Focus', 'AC-5136')

INSERT INTO Car(Manufacturer, Model, RegNumber)
VALUES ('Ford', 'Escort', 'GG-1056')

INSERT INTO EmployeeCar(EmployeeID, CarID)
VALUES (1, 1)

INSERT INTO EmployeeCar(EmployeeID, CarID)
VALUES (2, 2)

INSERT INTO Timeframe(Day, TimeFrom, TimeTo, EmployeeCarID)
VALUES ('Monday', '9:00:00', '11:00:00', 1)

INSERT INTO Timeframe(Day, TimeFrom, TimeTo, EmployeeCarID)
VALUES ('Monday', '13:00:00', '17:00:00', 1)

INSERT INTO Timeframe(Day, TimeFrom, TimeTo, EmployeeCarID)
VALUES ('Monday', '17:30:00', '18:00:00', 1)