package com.robopupu.feature.fsm.statemachine;

public class State_B2 extends State {

    public State_B2() {
        super(State_B.class, null);
    }

    @Override
    public void toB3() {
        toState(State_B3.class);
    }

    @Override
    protected void onEnter() {
        getController().onShowMessage("Entered State B2");
    }

    @Override
    protected void onExit() {
        getController().onShowMessage("Exited State B2");
    }
}
