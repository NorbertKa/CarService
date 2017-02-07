package modules;

import bean.ErrorResponse;

public class ControllerValidator {
    public static ErrorResponse checkQueryParam(String input, String parameter){
        ErrorResponse errorResponse = new ErrorResponse();
        if (input != null && input.length() == 0) {
            errorResponse.setError("Empty " + parameter);
            errorResponse.setCode(2);
            return errorResponse;
        }
        return null;
    }

    public static ErrorResponse checkQueryParam(Integer input, String parameter){
        ErrorResponse errorResponse = new ErrorResponse();
        if (input != null && input == 0) {
            errorResponse.setError("Empty " + parameter);
            errorResponse.setCode(2);
            return errorResponse;
        }
        return null;
    }

    public static ErrorResponse checkPagination(Integer startingFrom) {
        ErrorResponse errorResponse = new ErrorResponse();
        if (startingFrom != null && startingFrom == 0) {
            errorResponse.setError("Pagination starts with 1");
            errorResponse.setCode(2);
            return errorResponse;
        }
        return null;
    }
}
