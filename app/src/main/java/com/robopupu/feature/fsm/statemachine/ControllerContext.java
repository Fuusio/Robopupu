package com.robopupu.feature.fsm.statemachine;

import com.robopupu.api.fsm.StateMachineContext;

/**
 * {@link ControllerContext} is a sample of an interface that defines a context for a state machine.
 */
@StateMachineContext(SimpleStateMachine.class)
public interface ControllerContext {

    void setController(Controller controller);
}
