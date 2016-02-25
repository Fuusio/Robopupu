package com.robopupu.feature.fsm.statemachine;

public class State_C extends State {

    public State_C() {
        super(State.class, null);
    }

    @Override
    public void toB() {
        toHistoryState(State_B.class, false);
    }

    @Override
    protected void onEnter() {
        getController().onShowMessage("Entered State C");
    }

    @Override
    protected void onExit() {
        getController().onShowMessage("Exited State C");
    }

}
