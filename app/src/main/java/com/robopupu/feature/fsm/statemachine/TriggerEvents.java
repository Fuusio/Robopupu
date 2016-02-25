package com.robopupu.feature.fsm.statemachine;

import org.fuusio.api.fsm.StateMachineEvents;

/**
 * {@link TriggerEvents} ...
 */
@StateMachineEvents(SimpleStateMachine.class)
public interface TriggerEvents {

    void toB();
    void toCorD(int selector);
    void toB1();
    void toB2();
    void toB3();
    void toSelf();
}
