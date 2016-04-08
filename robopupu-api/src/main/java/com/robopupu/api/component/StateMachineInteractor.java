package com.robopupu.api.component;

import com.robopupu.api.fsm.StateMachine;

/**
 * {@link StateMachineInteractor} extends {@link AbstractInteractor} to provide an abstract
 * base class for implementing {@link Interactor}s that utilise {@link StateMachine}s.
 */
public abstract class StateMachineInteractor<T_StateMachine extends StateMachine>
        extends AbstractInteractor {

    private T_StateMachine mStateMachine;

    protected StateMachineInteractor() {
        mStateMachine = createStateMachine();
    }

    protected abstract T_StateMachine createStateMachine();
}
