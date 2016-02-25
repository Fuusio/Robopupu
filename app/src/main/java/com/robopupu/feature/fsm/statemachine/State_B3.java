package com.robopupu.feature.fsm.statemachine;

public class State_B3 extends State {

    public State_B3() {
        super(State_B.class, null);
    }

    @Override
    public void toB1() {
        toState(State_B1.class);
    }

    @Override
    public void toB2() {
        toState(State_B2.class);
    }

    @Override
    protected void onEnter() {
        getController().onShowMessage("Entered State B3");
    }

    @Override
    protected void onExit() {
        getController().onShowMessage("Exited State B3");
    }
}
