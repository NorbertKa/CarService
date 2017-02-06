package controllers;

import bean.EmployeeCar;
import bean.ErrorResponse;
import models.EmployeeCarModel;
import org.postgresql.util.PSQLException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/employeecars")
public class EmployeeCarController {
    EmployeeCarModel employeeCarModel = new EmployeeCarModel();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeCars(@QueryParam("startingFrom") Integer startingFrom, @QueryParam("employeeId") Integer employeeId, @QueryParam("carId") Integer carId) throws Exception {
        List<EmployeeCar> employeeCarList = new ArrayList<EmployeeCar>();

        if (startingFrom != null &&startingFrom == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Pagination starts with 1", 2);
            return Response.status(404).entity(errorResponse).build();
        }

        if (employeeId != null && employeeId == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty name", 2);
            return Response.status(404).entity(errorResponse).build();
        }

        if (carId != null && carId == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty name", 2);
            return Response.status(404).entity(errorResponse).build();
        }

        try {
            employeeCarList = employeeCarModel.getEmployeeCars(startingFrom, employeeId, carId);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 0);
            return Response.status(505).entity(errorResponse).build();
        }

        if(employeeCarList.size() == 0) {
            ErrorResponse errorResponse = new ErrorResponse("No employee cars found", 1);
            return Response.status(404).entity(errorResponse).build();
        }

        return Response.status(200).entity(employeeCarList).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeCarById(@PathParam("id") Integer id) {
        EmployeeCar employeeCar = new EmployeeCar();
        try {
            employeeCar = employeeCarModel.getEmployeeCar(id);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 0);
            return Response.status(500).entity(errorResponse).build();
        }

        if (!employeeCar.checkId()) {
            ErrorResponse errorResponse = new ErrorResponse("Employee car not found", 1);
            return Response.status(404).entity(errorResponse).build();
        }

        return Response.status(200).entity(employeeCar).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEmployeeCar(@FormParam("employeeId") Integer employeeId, @FormParam("carId") Integer carId) throws Exception {
        if (employeeId != null && employeeId == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty employeeId", 2);
            return Response.status(404).entity(errorResponse).build();
        } else if (employeeId == null) {
            ErrorResponse errorResponse = new ErrorResponse("Null employeeId", 4);
            return Response.status(404).entity(errorResponse).build();
        }

        if (carId != null && carId == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty carId", 2);
            return Response.status(404).entity(errorResponse).build();
        } else if (carId == null) {
            ErrorResponse errorResponse = new ErrorResponse("Null carId", 4);
            return Response.status(404).entity(errorResponse).build();
        }

        EmployeeCar employeeCar = new EmployeeCar();
        employeeCar.setEmployeeId(employeeId);
        employeeCar.setCarId(carId);
        try {
            employeeCarModel.insertEmployeeCar(employeeCar);
        } catch (PSQLException e) {
            ErrorResponse errorResponse = new ErrorResponse("Cant insert employeeCar", 5);
            return Response.status(404).entity(errorResponse).build();
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 0);
            return Response.status(505).entity(errorResponse).build();
        }
        return Response.status(200).entity(employeeCar).build();
    }
}
