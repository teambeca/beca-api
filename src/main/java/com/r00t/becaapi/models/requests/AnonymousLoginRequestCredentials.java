package com.r00t.becaapi.models.requests;

import javax.validation.constraints.NotBlank;

public class AnonymousLoginRequestCredentials {
    @NotBlank(message = "avatarTag field can't be blank")
    private String avatarTag;

    public String getAvatarTag() {
        return avatarTag;
    }

    public void setAvatarTag(String avatarTag) {
        this.avatarTag = avatarTag;
    }
}
