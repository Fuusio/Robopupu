package com.robopupu.feature.fsm.statemachine;

import org.fuusio.api.fsm.StateEngine;
import org.fuusio.api.fsm.StateMachine;

/**
 * {@link SimpleStateMachine} is a simple {@link StateMachine} implementation demonstrating
 * Fuusio-FSM library.
 */
public class SimpleStateMachine extends StateMachine {

    private final Controller mController;

    public SimpleStateMachine(final Controller controller) {
        mController = controller;
    }

    public Controller getController() {
        return mController;
    }

    @Override
    public void start() {
        start(State_A.class);
    }

    @Override
    protected void onStateEngineCreated(final StateEngine stateEngine) {
        ((State)stateEngine).setController(mController);
    }
}
