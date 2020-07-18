package com.r00t.becaapi.exceptions;

public class PermissionException extends BaseException {
    private static final int code = 401;
    private static final String reason = "unauthorized";
    private static final String message = "The user is not authorized to make the request.";

    public PermissionException(String parameter) {
        super(code, reason, parameter, message);
    }
}
