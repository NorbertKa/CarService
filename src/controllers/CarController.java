package controllers;

import bean.Car;
import bean.ErrorResponse;
import models.CarModel;
import modules.ControllerValidator;
import org.postgresql.util.PSQLException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/cars")
public class CarController {
    CarModel carModel = new CarModel();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCars(@QueryParam("startingFrom") Integer startingFrom, @QueryParam("regNumber") String regNumber, @QueryParam("manufacturer") String manufacturer) throws Exception {
        List<Car> carList = new ArrayList<Car>();

        ErrorResponse paginationCheck = ControllerValidator.checkPagination(startingFrom);
        if (paginationCheck != null) {
            return Response.status(404).entity(paginationCheck).build();
        }

        ErrorResponse regNumberCheck = ControllerValidator.checkQueryParam(regNumber, "regNumber");
        if (regNumberCheck != null) {
            return Response.status(404).entity(regNumberCheck).build();
        }

        ErrorResponse manufacturerCheck = ControllerValidator.checkQueryParam(manufacturer, "manufacturer");
        if (manufacturerCheck != null) {
            return Response.status(404).entity(manufacturerCheck).build();
        }

        try {
            carList = carModel.getCars(startingFrom, regNumber, manufacturer);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 0);
            return Response.status(505).entity(errorResponse).build();
        }

        if (carList.size() == 0) {
            ErrorResponse errorResponse = new ErrorResponse("No cars found", 1);
            return Response.status(404).entity(errorResponse).build();
        }

        return Response.status(200).entity(carList).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarById(@PathParam("id") Integer id) {
        Car car = new Car();
        try {
            car = carModel.getCar(id);
        }  catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 0);
            return Response.status(500).entity(errorResponse).build();
        }

        if (!car.checkId()) {
            ErrorResponse errorResponse = new ErrorResponse("Car not found", 1);
            return Response.status(404).entity(errorResponse).build();
        }

        return Response.status(200).entity(car).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCar(@FormParam("manufacturer") String manufacturer, @FormParam("model") String model, @FormParam("regNumber") String regNumber) {
        if (manufacturer != null && manufacturer.length() == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty manufacturer", 2);
            return Response.status(404).entity(errorResponse).build();
        } else if (manufacturer == null) {
            ErrorResponse errorResponse = new ErrorResponse("Null manufacturer", 4);
            return Response.status(404).entity(errorResponse).build();
        }

        if (model != null && model.length() == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty model", 2);
            return Response.status(404).entity(errorResponse).build();
        } else if (model == null) {
            ErrorResponse errorResponse = new ErrorResponse("Null model", 4);
            return Response.status(404).entity(errorResponse).build();
        }

        if (regNumber != null && regNumber.length() == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty regNumber", 2);
            return Response.status(404).entity(errorResponse).build();
        } else if (regNumber == null) {
            ErrorResponse errorResponse = new ErrorResponse("Null regNumber", 4);
            return Response.status(404).entity(errorResponse).build();
        }

        Car car = new Car();
        car.setManufacturer(manufacturer);
        car.setModel(model);
        car.setRegNumber(regNumber);
        try {
            carModel.insertCar(car);
        } catch (PSQLException e) {
            ErrorResponse errorResponse = new ErrorResponse("Car already exists", 5);
            return Response.status(404).entity(errorResponse).build();
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 0);
            return Response.status(505).entity(errorResponse).build();
        }

        return Response.status(200).entity(car).build();
    }
}
