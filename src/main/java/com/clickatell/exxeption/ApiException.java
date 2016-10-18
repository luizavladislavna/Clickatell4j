package com.clickatell.exxeption;

import com.clickatell.entity.IJsonEntity;

@IJsonEntity
public class ApiException extends ClickatellException {

    private static final long serialVersionUID = -3228320166955630014L;

    private final Integer code;
    private final String moreInfo;
    private final Integer status;

    /**
     * Create a new API Exception.
     *
     * @param message exception message
     */
    public ApiException(final String message) {
        this(message, null, null, null, null);
    }
    /**
     * Create a new API Exception.
     *
     * @param message exception message
     * @param code exception code
     */
    public ApiException(final String message, final Integer code) {
        this(message, code, null, null, null);
    }

    /**
     * Create a new API Exception.
     *
     * @param message exception message
     * @param cause cause of the exception
     */
    public ApiException(final String message, final Throwable cause) {
        this(message, null, null, null, cause);
    }

    /**
     * Create a new API Exception.
     *
     * @param message exception message
     * @param code exception code
     * @param moreInfo more information if available
     * @param status status code
     * @param cause cause of the exception* @param cause
     */
    public ApiException(final String message, final Integer code, final String moreInfo, final Integer status,
                        final Throwable cause) {
        super(message, cause);
        this.code = code;
        this.moreInfo = moreInfo;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public int getStatusCode() {
        return status;
    }
}
