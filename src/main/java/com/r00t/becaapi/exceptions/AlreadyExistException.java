package com.r00t.becaapi.exceptions;

public class AlreadyExistException extends BaseException {
    private static final int code = 409;
    private static final String reason = "conflict";
    private static final String message = "The API request cannot be completed because the requested operation would conflict with an existing item.";

    public AlreadyExistException(String parameter) {
        super(code, reason, parameter, message);
    }
}
