package controllers;

import bean.ErrorResponse;
import bean.Timeframe;
import models.TimeframeInsertException;
import models.TimeframeModel;
import modules.ControllerValidator;
import org.postgresql.util.PSQLException;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Path("/timeframes")
public class TimeframeController {
    TimeframeModel timeframeModel = new TimeframeModel();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTimeframes(@QueryParam("startingFrom") Integer startingFrom, @QueryParam("employeeCarId") Integer employeeCarId) throws Exception {
        List<Timeframe> timeframeList= new ArrayList<Timeframe>();

        ErrorResponse paginationCheck = ControllerValidator.checkPagination(startingFrom);
        if (paginationCheck != null) {
            return Response.status(404).entity(paginationCheck).build();
        }

        ErrorResponse employeeCarIdCheck = ControllerValidator.checkQueryParam(employeeCarId, "employeeCarId");
        if (employeeCarIdCheck != null) {
            return Response.status(404).entity(employeeCarIdCheck).build();
        }

        try {
            timeframeList = timeframeModel.getTimeframes(startingFrom, employeeCarId);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 0);
            return Response.status(505).entity(errorResponse).build();
        }

        if(timeframeList.size() == 0) {
            ErrorResponse errorResponse = new ErrorResponse("No timeframes found", 1);
            return Response.status(404).entity(errorResponse).build();
        }

        return Response.status(200).entity(timeframeList).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTimeframe(@PathParam("id") Integer id) throws Exception {
        Timeframe timeframe = new Timeframe();
        try {
            timeframe = timeframeModel.getTimeframe(id);
        }  catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 0);
            return Response.status(500).entity(errorResponse).build();
        }

        if (!timeframe.checkId()) {
            ErrorResponse errorResponse = new ErrorResponse("Timeframe not found", 1);
            return Response.status(404).entity(errorResponse).build();
        }

        return Response.status(200).entity(timeframe).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTimeframe(@FormParam("day") String day, @FormParam("timeFrom") String timeFrom, @FormParam("timeTo") String timeTo, @FormParam("employeeCarId") Integer employeeCarId) {
        if (day != null && day.length() == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty day", 2);
            return Response.status(404).entity(errorResponse).build();
        } else if (day == null) {
            ErrorResponse errorResponse = new ErrorResponse("Null day", 4);
            return Response.status(404).entity(errorResponse).build();
        }

        if (timeFrom != null && timeFrom.length() == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty timeFrom", 2);
            return Response.status(404).entity(errorResponse).build();
        } else if (timeFrom == null) {
            ErrorResponse errorResponse = new ErrorResponse("Null timeFrom", 4);
            return Response.status(404).entity(errorResponse).build();
        }

        if (timeTo != null && timeTo.length() == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty timeTo", 2);
            return Response.status(404).entity(errorResponse).build();
        } else if (timeTo == null) {
            ErrorResponse errorResponse = new ErrorResponse("Null timeTo", 4);
            return Response.status(404).entity(errorResponse).build();
        }

        if (employeeCarId != null && employeeCarId == 0) {
            ErrorResponse errorResponse = new ErrorResponse("Empty employeeCarId", 2);
            return Response.status(404).entity(errorResponse).build();
        } else if (employeeCarId == null) {
            ErrorResponse errorResponse = new ErrorResponse("Null employeeCarId", 4);
            return Response.status(404).entity(errorResponse).build();
        }


        Timeframe timeframe = new Timeframe();
        timeframe.setDay(day);
        timeframe.setTimeFrom(Time.valueOf(timeFrom));
        timeframe.setTimeTo(Time.valueOf(timeTo));
        timeframe.setEmployeeCarId(employeeCarId);



        try {
            timeframeModel.insertTimeframe(timeframe);
        } catch (PSQLException | TimeframeInsertException e) {
            ErrorResponse errorResponse = new ErrorResponse("Cant insert timeframe", 5);
            return Response.status(404).entity(errorResponse).build();
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 0);
            return Response.status(505).entity(errorResponse).build();
        }
        return Response.status(200).entity(timeframe).build();
    }
}
