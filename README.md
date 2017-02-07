# CarService

## Kā palaist servisu:
*main funkcija atrodas src/controllers/RestServer*

Ka arī esmu izveidojis jaru iekšā out/artifacts/CarService_jar, kuru var palaist šādi:

`java -jar CarService.jar <POSTGRESQL_HOST> <POSTGRESQL_PORT> <POSTGRESQL_USERNAME> <POSTGRESQL_DB> <POSTGRESQL_PASSWORD>`

## Kā palaist *frontend*

frontend kods atrodas *frontend* mapītē, to palaist var ar komandām (secīgi)

`npm install`

`npm run dev`

Vienīgā problēma ir tādā ka man nesanācā salabot CORS uz servera, tādēļ browseri kuru izmantojat vajag palaist unsecure mode (piemēram chrome: `--disable-web-security`)


Routes:

| Route         | Method           | Query/Form Parameters  | Description  |
| ------------- |:-------------:| -----:| -----:|
| /employees      | GET |startingFrom, name  | Atgriež visus darbiniekus
| /employees/{id}      | GET      |    | Atgriež pieprasīto darbinieku (pēc ID)
| /employees/{id}/cars      | GET      |    | Atgriež savienojumus ar piesaistītajām mašīnām
| /employees | POST      |    name, surname | Pievieno jaunu darbinieku (name, surname)
| /cars      | GET | startingFrom, regNumber, manufacturer  | Atgriež visas mašīnas
| /cars/{id}      | GET      |    | Atgriež pieprasīto mašīnu (pēc ID)
| /cars | POST      |    manufacturer, model, regNumber | Izveido jaunu mašīnu (pēc ID)
| /employeecars      | GET |  startingFrom, employeeId, carId | Atgriež visas mašīnu un darbinieku saistības
| /employeecars/{id}      | GET      |    | Atgriež specifisko pieprasīto saistību (pēc ID)
| /employeecars | POST      |    employeeId, carId | Izveido jaunu darbinieka un mašīnas saistību
| /timeframes      | GET |  startingFrom, employeeCarId| Atgirēz visus mašīnas izmantošanas laikus
| /timeframes/{id}      | GET      |    | Atgriež specifisku mašīnas izmantošanas laiku
| /timeframes | POST      |    day, timeFrom, timeTo, employeeCarId | Izveido jaunu mašīnas izmantošanas laiku

