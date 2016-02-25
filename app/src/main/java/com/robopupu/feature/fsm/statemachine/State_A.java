package com.robopupu.feature.fsm.statemachine;

public class State_A extends State {

    public State_A() {
        super(State.class, null);
    }

    @Override
    public void toB() {
        toState(State_B.class);
    }

    @Override
    protected void onEnter() {
        getController().onShowMessage("Entered State A");
    }

    @Override
    protected void onExit() {
        getController().onShowMessage("Exited State A");
    }
}
