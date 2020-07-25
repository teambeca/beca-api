package com.r00t.becaapi.models.requests;

import java.util.List;

public class Seed {
    private String type;
    private List<String> texts;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }
}
