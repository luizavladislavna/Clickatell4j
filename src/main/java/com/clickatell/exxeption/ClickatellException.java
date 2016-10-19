package com.clickatell.exxeption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
public abstract class ClickatellException extends RuntimeException {

    private static final long serialVersionUID = 2516935680980388130L;

    public ClickatellException (final String message) {
        this(message, null);
    }

    public ClickatellException (final String message, final Throwable cause) {
        super(message, cause);
    }
}
