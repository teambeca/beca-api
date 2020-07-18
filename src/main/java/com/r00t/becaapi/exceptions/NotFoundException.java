package com.r00t.becaapi.exceptions;

public class NotFoundException extends BaseException {
    private static final int code = 404;
    private static final String reason = "notFound";
    private static final String message = "The requested operation failed because a resource associated with the request could not be found.";

    public NotFoundException(String parameter) {
        super(code, reason, parameter, message);
    }
}
