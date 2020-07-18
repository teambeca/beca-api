package com.r00t.becaapi.exceptions;

public class BaseException extends Throwable {
    private final int errorCode;
    private final String errorReason;
    private final String errorParameter;
    private final String errorMessage;

    public BaseException(int errorCode, String errorReason, String errorParameter, String errorMessage) {
        this.errorCode = errorCode;
        this.errorReason = errorReason;
        this.errorParameter = errorParameter;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public String getErrorParameter() {
        return errorParameter;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
