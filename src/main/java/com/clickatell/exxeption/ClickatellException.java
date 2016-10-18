package com.clickatell.exxeption;

import com.clickatell.entity.IJsonEntity;

@IJsonEntity
public abstract class ClickatellException extends RuntimeException {

    private static final long serialVersionUID = 2516935680980388130L;

    public ClickatellException (final String message) {
        this(message, null);
    }

    public ClickatellException (final String message, final Throwable cause) {
        super(message, cause);
    }
}
