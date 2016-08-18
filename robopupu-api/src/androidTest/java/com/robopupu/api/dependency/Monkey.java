package com.robopupu.api.dependency;

public class Monkey {

    private final Banana banana;

    public Monkey(Banana banana) {
        this.banana = banana;
    }

    public Banana getBanana() {
        return banana;
    }
}
