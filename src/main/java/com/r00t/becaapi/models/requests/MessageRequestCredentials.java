package com.r00t.becaapi.models.requests;

import javax.validation.constraints.NotBlank;

public class MessageRequestCredentials {
    @NotBlank(message = "")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
