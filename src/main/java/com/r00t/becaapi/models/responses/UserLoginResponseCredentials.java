package com.r00t.becaapi.models.responses;

public class UserLoginResponseCredentials {
    private String username;
    private String role;
    private String avatarTag;
    private int place;
    private long score;
    private long creationDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatarTag() {
        return avatarTag;
    }

    public void setAvatarTag(String avatarTag) {
        this.avatarTag = avatarTag;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }
}
