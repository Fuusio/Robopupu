package com.robopupu.feature.fsm.statemachine;

import org.fuusio.api.fsm.StateMachineContext;

/**
 * {@link ControllerContext} ...
 */
@StateMachineContext(SimpleStateMachine.class)
public interface ControllerContext {

    void setController(Controller controller);
}
