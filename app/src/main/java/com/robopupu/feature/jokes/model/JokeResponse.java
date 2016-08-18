package com.robopupu.feature.jokes.model;

public class JokeResponse {

    private String type;

    private JokeValue value;

    public JokeResponse() {
    }

    public String getType() {
        return type;
    }

    public JokeValue getValue() {
        return value;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setValue(final JokeValue value) {
        this.value = value;
    }
}
