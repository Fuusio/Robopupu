package com.robopupu.feature.fsm.statemachine;

public class State_B extends State {

    public State_B() {
        super(State.class, State_B1.class);
    }

    @Override
    public void toCorD(int selector) {

        if (selector == 1) {
            transitTo(State_C.class);
        } else {
            transitTo(State_D.class);
        }
    }

    @Override
    protected State enterEntryPoint(int entryPoint) {
        if (entryPoint == 1) {
            return transitTo(State_B1.class);
        } else if (entryPoint == 2) {
            return transitTo(State_B2.class);
        } else if (entryPoint == 3) {
            return transitTo(State_B3.class);
        }
        return null;
    }

    @Override
    protected void onEnter() {
        getController().onShowMessage("Entered State B");
    }

    @Override
    protected void onExit() {
        getController().onShowMessage("Exited State B");
    }
}
