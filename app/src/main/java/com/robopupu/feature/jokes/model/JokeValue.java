package com.robopupu.feature.jokes.model;

import com.google.gson.annotations.SerializedName;

public class JokeValue {

    @SerializedName("id")
    private int mId;

    @SerializedName("joke")
    private String mJoke;

    @SerializedName("categories")
    private String[] mCategories;

    public JokeValue() {
    }

    public int getId() {
        return mId;
    }

    public String getJoke() {
        return mJoke;
    }

    public void setId(final int id) {
        mId = id;
    }

    public void setJoke(final String joke) {
        mJoke = joke;
    }

    public String[] getCategories() {
        return mCategories;
    }

    public void setCategories(final String[] categories) {
        mCategories = categories;
    }
}
