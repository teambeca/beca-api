package com.r00t.becaapi.exceptions;

public class ServiceUnavailableException extends BaseException {
    private static final int code = 503;
    private static final String reason = "backendError";
    private static final String message = "A backend error occurred.";

    public ServiceUnavailableException(String parameter) {
        super(code, reason, parameter, message);
    }
}
