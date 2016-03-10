package com.robopupu.feature.fsm.statemachine;

public class State_B1 extends State {

    public State_B1() {
        super(State_B.class, null);
    }

    @Override
    public void toB2() {
        transitTo(State_B2.class);
    }

    @Override
    protected void onEnter() {
        getController().onShowMessage("Entered State B1");
    }

    @Override
    protected void onExit() {
        getController().onShowMessage("Exited State B1");
    }
}
