package org.fuusio.api.fsm;
/*
 * Copyright (C) 2016 Marko Salmela.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.fuusio.api.util.LifecycleState;

import java.lang.reflect.Method;

/**
 * {@link StateMachine} provides an abstract base class for implementing a Finite State Machine
 * (FMS). An instance of concrete {@link StateMachine} uses a code generated concrete implementation
 * of {@link StateEngine} to implement and execute the state machine.
 */
public abstract class StateMachine implements StateEngineObserver {

    private LifecycleState mLifecycleState;
    private boolean mResetted;
    private StateEngine<? extends StateEngine> mStateEngine;

    protected StateMachine() {
        mLifecycleState = LifecycleState.DORMANT;
        mResetted = false;
    }

    /**
     * Gets the {@link StateEngine} that is an instance of concrete implementation of
     * a {@link StateEngine} code generated by  Fuusio FSM Annotation Processor.
     * @return A {@link StateEngine}.
     */
    @SuppressWarnings("unchecked")
    public final <T extends StateEngine> T getStateEngine() {
        return (T) mStateEngine;
    }

    /**
     * Gets the current state {@link StateEngine} that is an instance of code generated class.
     * @return The current state as a {@link StateEngine}. May not return {@code null} for started
     * state machine.
     */
    @SuppressWarnings("unchecked")
    public final <T extends StateEngine> T getCurrentState() {
        return (T) getCurrentState(mStateEngine.getCurrentState());
    }

    @SuppressWarnings("unchecked")
    private <T extends StateEngine> T getCurrentState(T state) {
        final T currentState =(T)state.getCurrentState();

        if (currentState != null) {
            return (T)getCurrentState(currentState);
        } else {
            return state;
        }
    }
    /**
     * Tests if this {@link StateMachine} has been started.
     * @return A {@code boolean} value.
     */
    public boolean isStarted() {
        return mLifecycleState.isStarted();
    }

    /**
     * Initializes this {@link StateMachine} for execution.
     * @param initialStateClass
     */
    @SuppressWarnings("unchecked")
    private final void initialize(final Class<? extends StateEngine> initialStateClass) {

        mStateEngine = createStateEngine();

        if (mStateEngine == null) {
            final String stateEngineClassName = getClass().getPackage().getName() + ".State";

            try {
                final Class<? extends StateEngine> stateEngineClass = (Class<? extends StateEngine>) Class.forName(stateEngineClassName);
                final Method method = stateEngineClass.getMethod("create", initialStateClass.getClass());
                mStateEngine = (StateEngine)method.invoke(null, initialStateClass);
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate StateEngine class: " + stateEngineClassName);
            }
        }

        onStateEngineCreated(mStateEngine);

        mLifecycleState = LifecycleState.CREATED;
        mStateEngine.setObserver(this);
    }

    protected void onStateEngineCreated(final StateEngine stateEngine) {
        // By default do nothing
    }

    /**
     * Creates the {@link StateEngine}. This method can be overridden in concrete implementations,
     * but if not overridden, the default instantiation method will be used.
     * @return A {@link StateEngine}. The default implementation returns {@code null}.
     */
    protected StateEngine createStateEngine() {
        return null;
    }

    /**
     * Starts the concrete implementation of {@link StateMachine}.
     * @param initialStateClass A {@link Class} that implements an initial state for the state machine.
     * @return The entered state as a class derived from {@link StateEngine}.
     */
    @SuppressWarnings("unchecked")
    protected final <T extends StateEngine> T start(final Class<T> initialStateClass) {

        if (mLifecycleState.isDormant()) {
            initialize(initialStateClass);
        }

        if (mLifecycleState.isCreated() || mLifecycleState.isStopped() && mResetted) {
            mStateEngine.start();
            mResetted = false;
            mLifecycleState = LifecycleState.STARTED;
            return getCurrentState();
        } else {
            throw new IllegalStateException("A stopped StateMachine has to be resetted before restarting");
        }
    }

    /**
     * Starts this {@link StateMachine} by starting the concrete implementation of {@link StateEngine}.
     * The concrete implementation has to override this method and invoke
     * {@link StateMachine#start(Class)} method.
     */
    public abstract void start();

    /**
     * Stops this {@link StateMachine}. A stopped {@link StateMachine} can be restarted after resetting
     * it.
     */
    public final void stop() {
        mLifecycleState = LifecycleState.STOPPED;
        mStateEngine.stop();
    }

    /**
     * Reset this {@link StateMachine}. A {@link StateMachine} can be restarted after resetting.
     */
    public final void reset() {
        if (mLifecycleState.isStarted()) {
            stop();
        }
        mStateEngine.reset();
        mResetted = true;
    }

    @Override
    public void onDispose(final StateEngine stateEngine) {
        // By default do nothing
    }

    @Override
    public void onError(final StateEngine stateEngine, final StateEngine.Error error, final String message) {
        // By default do nothing
    }

    @Override
    public void onReset(final StateEngine stateEngine) {
        // By default do nothing
    }

    @Override
    public void onStart(final StateEngine stateEngine) {
        // By default do nothing
    }

    @Override
    public void onStop(final StateEngine stateEngine) {
        // By default do nothing
    }
}
