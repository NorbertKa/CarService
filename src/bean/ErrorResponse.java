package bean;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
    private String error = null;
    private Integer code = null;

    public ErrorResponse() {}

    public ErrorResponse(String error, Integer code) {
        if (error != null) {
            this.error = error;
        }
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        if (error != null) {
            this.error = error;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        if (code != null) {
            this.code = code;
        }
    }
}
