package com.r00t.becaapi.models.requests;

import javax.validation.constraints.NotBlank;

public class UserLoginRequestCredentials {
    @NotBlank(message = "")
    private String username;
    private String password;
    @NotBlank(message = "")
    private String email;
    @NotBlank(message = "")
    private String avatarTag;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarTag() {
        return avatarTag;
    }

    public void setAvatarTag(String avatarTag) {
        this.avatarTag = avatarTag;
    }
}
