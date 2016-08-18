package com.robopupu.feature.fsm.statemachine;

import com.robopupu.api.fsm.StateEngine;
import com.robopupu.api.fsm.StateMachine;

/**
 * {@link SimpleStateMachine} is a simple {@link StateMachine} implementation demonstrating
 * Fuusio-FSM library.
 */
public class SimpleStateMachine extends StateMachine {

    private final Controller controller;

    public SimpleStateMachine(final Controller controller) {
        this.controller = controller;
    }

    @Override
    public void start() {
        start(State_A.class);
    }

    @Override
    protected void onStateEngineCreated(final StateEngine stateEngine) {
        ((State)stateEngine).setController(controller);
    }
}
