package com.robopupu.feature.fsm.statemachine;

public class State_D extends State {

    public State_D() {
        super(State.class, null);
    }

    @Override
    public void toB() {
        toState(State_B.class, 3);
    }

    @Override
    public void toSelf() {
        getController().onShowMessage("Event received in D");
    }

    @Override
    protected void onEnter() {
        getController().onShowMessage("Entered State D");
    }

    @Override
    protected void onExit() {
        getController().onShowMessage("Exited State D");
    }
}
