package com.robopupu.feature.jokes.model;

import com.google.gson.annotations.SerializedName;

public class JokeResponse {

    @SerializedName("type")
    private String mType;

    @SerializedName("value")
    private JokeValue mValue;

    public JokeResponse() {
    }

    public String getType() {
        return mType;
    }

    public JokeValue getValue() {
        return mValue;
    }

    public void setType(final String type) {
        mType = type;
    }

    public void setValue(final JokeValue value) {
        mValue = value;
    }
}
