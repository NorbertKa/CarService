package controllers;

import bean.Employee;
import bean.ErrorResponse;
import models.EmployeeModel;
import org.postgresql.util.PSQLException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/employees")
public class EmployeeController {
    EmployeeModel employeeModel = new EmployeeModel();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees(@QueryParam("startingFrom") Integer startingFrom, @QueryParam("name") String name, @Context final HttpServletResponse response) throws Exception {
        List<Employee> employeeList = new ArrayList<Employee>();

        if (name != null && name.length() == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty name", 2);
            return Response.status(404).entity(errorResponse).build();
        }


        try {
            employeeList =  employeeModel.getEmployees(startingFrom, name);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 0);
            return Response.status(505).entity(errorResponse).build();
        }

        if (startingFrom != null &&startingFrom == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Pagination starts with 1", 2);
            return Response.status(404).entity(errorResponse).build();
        }

        if(employeeList.size() == 0) {
            ErrorResponse errorResponse = new ErrorResponse("No employees found", 1);
            return Response.status(404).entity(errorResponse).build();
        }

        return Response.status(200).entity(employeeList).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("id") Integer id) {
        Employee employee = new Employee();
        try {
            employee = employeeModel.getEmployee(id);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 0);
            return Response.status(500).entity(errorResponse).build();
        }

        if (!employee.checkId()) {
            ErrorResponse errorResponse = new ErrorResponse("Employee not found", 1);
            return Response.status(404).entity(errorResponse).build();
        }

        return Response.status(200).entity(employee).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEmployee(@FormParam("name") String name, @FormParam("surname") String surname) {

        if (name != null && name.length() == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty name", 2);
            return Response.status(404).entity(errorResponse).build();
        } else if (name == null) {
            ErrorResponse errorResponse = new ErrorResponse("Null name", 4);
            return Response.status(404).entity(errorResponse).build();
        }

        if (surname != null && surname.length() == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty surname", 2);
            return Response.status(404).entity(errorResponse).build();
        } else if (surname == null) {
            ErrorResponse errorResponse = new ErrorResponse("Null surname", 4);
            return Response.status(404).entity(errorResponse).build();
        }

        Employee employee = new Employee();
        employee.setName(name);
        employee.setSurname(surname);
        try {
            employeeModel.insertEmployee(employee);
        } catch (PSQLException e) {
            ErrorResponse errorResponse = new ErrorResponse("Employee already exists", 5);
            return Response.status(404).entity(errorResponse).build();
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 0);
            return Response.status(505).entity(errorResponse).build();
        }
        return Response.status(200).entity(employee).build();
    }
}
