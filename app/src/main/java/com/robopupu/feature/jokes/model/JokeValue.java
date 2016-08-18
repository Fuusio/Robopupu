package com.robopupu.feature.jokes.model;

import com.google.gson.annotations.SerializedName;

public class JokeValue {

    private int id;

    private String joke;
    
    private String[] categories;

    public JokeValue() {
    }

    public int getId() {
        return id;
    }

    public String getJoke() {
        return joke;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setJoke(final String joke) {
        this.joke = joke;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(final String[] categories) {
        this.categories = categories;
    }
}
