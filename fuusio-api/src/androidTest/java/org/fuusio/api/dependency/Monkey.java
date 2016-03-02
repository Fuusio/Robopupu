package org.fuusio.api.dependency;

public class Monkey {

    private final Banana mBanana;

    public Monkey(Banana banana) {
        mBanana = banana;
    }

    public Banana getBanana() {
        return mBanana;
    }
}
