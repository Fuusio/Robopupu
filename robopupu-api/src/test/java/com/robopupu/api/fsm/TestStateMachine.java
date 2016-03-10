package com.robopupu.api.fsm;

import com.robopupu.api.fsm.state.PowerOffState;

public class TestStateMachine extends StateMachine {

    private final CoffeeMaker mCoffeeMaker;

    public TestStateMachine(final CoffeeMaker coffeeMaker) {
        mCoffeeMaker = coffeeMaker;
    }

    @Override
    public void start() {
        start(PowerOffState.class);
    }

    @Override
    protected void onStateEngineCreated(final StateEngine stateEngine) {
        ((State)stateEngine).setCoffeeMaker(mCoffeeMaker);
    }
}
