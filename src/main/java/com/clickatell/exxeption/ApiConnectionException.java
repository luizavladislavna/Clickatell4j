package com.clickatell.exxeption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
public class ApiConnectionException extends ClickatellException {

    private static final long serialVersionUID = 6354388724599793830L;

    public ApiConnectionException (final String message) {
        super(message);
    }

    public ApiConnectionException (final String message, final Throwable cause) {
        super(message, cause);
    }

}
