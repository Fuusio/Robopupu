/*
 * Copyright (C) 2000-2014 Marko Salmela.
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
package com.robopupu.api.fsm;

import java.util.HashMap;
import java.util.HashSet;

/**
 * {@link StateEngine} is an abstract class that is used in code generation by annotation processor
 * for creating concrete {@link StateEngine} implementations for {@link StateMachine}s. All concrete
 * state classes are derived from the code generated implemetation of {@link StateEngine}.
 * @param <T_State>
 */
public abstract class StateEngine<T_State extends StateEngine> {

    /**
     * {@link Error} defines error types and messages for {@link StateEngine}.
     */
    public enum Error {

        ERROR_UNKNOWN("An unknown state machine error occurred while in State: %s"),
        ERROR_STATE_REENTERED("State: %s re-entered"),
        ERROR_UNHANDLED_EVENT("Unhandled event: %s received while in State: %s"),
        ERROR_UNHANDLED_ENTRY_POINT("Unhandled entry point: %s in State: %s"),
        ERROR_UNHANDLED_DEEP_HISTORY("Unhandled deep history in State: %s"),
        ERROR_UNHANDLED_SHALLOW_HISTORY("Unhandled shallow history in State: %s");

        private final String mDescription;

        Error(final String description) {
            mDescription = description;
        }

        public final String getDescription(final Object... args) {
            return String.format(mDescription, args);
        }
    }

    /**
     * A {@link HashMap} containing either 1) the substates of a state if this instance of {@link StateEngine}
     * represents a state or 2) the top-level states if this instance of {@link StateEngine}
     * represents a state machine.
     */
    protected final HashSet<T_State> mSubStates;

    /**
     * The current state among the substates of this state.
     */
    protected T_State mCurrentState;

    /**
     * A {@link Class} specifying the initial state among the substates of this state.
     */
    protected Class<? extends T_State> mInitialStateClass;

    /**
     * A {@link HashMap} used for caching state instances. The {@link Class}es of states can be used
     * as keys to the {@link HashMap}, because each state object has its own {@link Class}.
     */
    protected HashMap<Class<?>, T_State> mStateCache;

    /**
     * The reference to an instance of {@link StateEngine} that represents a state machine. All
     * instances of concrete implementations of {@link StateEngine} that represent individual
     * states have a reference to the state machine.
     */
    protected T_State mStateEngine;

    /**
     * The class of the super state of the state represented by this instance of {@link StateEngine}.
     * If this instance of {@link StateEngine} is a state machine, then this field has {@code null}
     * value.
     */
    protected Class<? extends T_State> mSuperStateClass;

    /**
     * The super state of the state represented by this instance of {@link StateEngine}. If this
     * instance of {@link StateEngine} is a state machine, then this field has {@code null} value.
     */
    protected T_State mSuperState;

    /**
     * The {@link StateEngineObserver} that receives events from this {@link StateEngine}.
     */
    private StateEngineObserver mObserver;

    /**
     * Private default constructor.
     */
    private StateEngine() {
        mSubStates = new HashSet<>();
    }

    /**
     * This constructor is to be used by a concrete implementation of {@link StateEngine}.
     *
     * @param initialStateClass The {@link Class} of a state that is the initial state to be entered
     *                          at top-level when the represented state machine is started.
     */
    @SuppressWarnings("unchecked")
    protected StateEngine(final Class<? extends T_State> initialStateClass) {
        this();

        mInitialStateClass = initialStateClass;

        // The following two field are initialised only in this constructor
        mStateEngine = (T_State) this;
        mStateCache = new HashMap<>();

        // Store this state engine state to cache of state instances
        mStateCache.put(getClass(), (T_State) this);
    }

    /**
     * This constructor is to be used by all actual state classes which are extended from the concrete
     * implementation of this {@link StateEngine}.
     *
     * @param superStateClass   The {@link Class} of a state that is the super state of the state using
     *                          this constructor.
     * @param initialStateClass The {@link Class} of a state that is the initial state to be entered
     *                          at top-level when the represented state machine is started.
     */
    protected StateEngine(final Class<? extends T_State> superStateClass, final Class<? extends T_State> initialStateClass) {
        this();

        mInitialStateClass = initialStateClass;
        mSuperStateClass = superStateClass;
    }

    /**
     * Gets the {@link StateEngineObserver} assigned for this {@link StateEngine}.
     * @return A {@link StateEngineObserver}.
     */
    public StateEngineObserver getObserver() {
        if (isStateEngine()) {
            return mObserver;
        } else {
            return getStateEngine().getObserver();
        }
    }

    /**
     * Sets the {@link StateEngineObserver} assigned for this {@link StateEngine}.
     * @param observer A {@link StateEngineObserver}.
     */
    public final void setObserver(final StateEngineObserver observer) {
        mObserver = observer;
    }

    /**
     * Gets the super state of this state.
     *
     * @param <T> A type parameter for the state type.
     * @return The state object.
     */
    @SuppressWarnings("unchecked")
    protected final <T extends T_State> T getSuperState() {
        return (T) mSuperState;
    }

    /**
     * Gets the current state of this {@link StateEngine} or state.
     *
     * @param <T> A type parameter for the state type.
     * @return A state object.
     */
    @SuppressWarnings("unchecked")
    public final <T extends StateEngine> T getCurrentState() {
        return (T) mCurrentState;
    }

    /**
     * Sets the current state of this {@link StateEngine} or state.
     *
     * @param state A state object representing the new current state.
     */
    protected final void setCurrentState(final T_State state) {
        mCurrentState = state;
    }

    /**
     * Gets the reference to a {@link StateEngine}.
     *
     * @return A {@link StateEngine}.
     */
    protected final T_State getStateEngine() {
        return mStateEngine;
    }

    /**
     * Tests if this instance of {@link StateEngine} represents a state machine and not a state
     * that belongs into it.
     *
     * @return A {@code boolean} value.
     */
    protected final boolean isStateEngine() {
        return (this == getStateEngine());
    }

    /**
     * Gets an instance of the specified state.
     *
     * @param stateClass A state object {@link Class}.
     * @return A state object. May not return {@code null}.
     */
    @SuppressWarnings("unchecked")
    protected final T_State getState(final Class<? extends T_State> stateClass) {

        if (isStateEngine()) {
            T_State state = mStateCache.get(stateClass);

            if (state == null) {
                try {
                    state = stateClass.newInstance();

                    state.mStateEngine = this.mStateEngine;
                    state.mSuperState = getState(state.mSuperStateClass);
                    state.mSuperState.mSubStates.add(state);

                    mStateCache.put(stateClass, state);

                } catch (Exception e) {
                    // Do nothing
                }
            }
            return state;
        } else {
            return (T_State) getStateEngine().getState(stateClass);
        }
    }

    /**
     * Causes transition from the current state to the specified state.
     *
     * @param stateClass A {@link Class} specifying the target state for the state transition.
     * @return The current state.
     */
    protected final T_State transitTo(final Class<? extends T_State> stateClass) {
        return transitTo(stateClass, 0);
    }

    /**
     * Causes transition from the current state to the specified state optionally via an entry point
     *
     * @param stateClass A {@link Class} specifying the target state for the state transition.
     * @param entryPoint A {@code int} value specifying if the optional entry point. Value zero
     *                   represents a non entry point.
     * @return The current state.
     */
    @SuppressWarnings("unchecked")
    protected final T_State transitTo(final Class<? extends T_State> stateClass, final int entryPoint) {

        if (isStateEngine()) {
            final T_State currentState = getCurrentState();

            if (currentState != null) {

                if (currentState.getClass().equals(stateClass)) {
                    onError(currentState, Error.ERROR_STATE_REENTERED);
                    return currentState;
                }
            }

            T_State newState = getState(stateClass);
            setCurrentState(newState);

            if (currentState != null && !newState.isSuperState(currentState)) {
                currentState.exit(newState);
            }

            // newState.onEnter();

            return (T_State) newState.enter(entryPoint);
        } else {
            return (T_State) getStateEngine().transitTo(stateClass, entryPoint);
        }
    }

    /**
     * Causes transition from the current state to the specified history state via a deep or
     * shallow history point.
     *
     * @param stateClass  A {@link Class} specifying the target state for the state transition.
     * @param deepHistory A {@code boolean} value specifying if the state is entered via a deep
     *                    history point instead of shallow history point.
     * @return The current state.
     */
    @SuppressWarnings("unchecked")
    protected final T_State toHistoryState(final Class<? extends T_State> stateClass, final boolean deepHistory) {

        if (isStateEngine()) {
            final T_State oldCurrentState = getCurrentState();

            if (oldCurrentState != null) {

                if (oldCurrentState.getClass().equals(stateClass)) {
                    onError(oldCurrentState, Error.ERROR_STATE_REENTERED);
                    return oldCurrentState;
                }
            }

            T_State newCurrentState = getState(stateClass);

            if (oldCurrentState != null && !newCurrentState.isSuperState(oldCurrentState)) {
                oldCurrentState.exit(newCurrentState);
            }

            newCurrentState.onEnter();

            if (deepHistory) {
                newCurrentState = (T_State) newCurrentState.enterDeepHistory();
            } else {
                newCurrentState = (T_State) newCurrentState.enterShallowHistory();
            }

            mCurrentState = newCurrentState;
            mCurrentState.onEnter();
            return mCurrentState;
        } else {
            return (T_State) getStateEngine().toHistoryState(stateClass, deepHistory);
        }
    }

    /**
     * Enters the represented by this instance of {@link StateEngine}. If the state is entered via
     * an entry point the index of the entry point has to be given and it has to be greater than zero.
     *
     * @param entryPoint The index of the entry point to be entered. Value zero represents a non
     *                   entry point.
     * @return The current state.
     */
    @SuppressWarnings("unchecked")
    protected final T_State enter(final int entryPoint) {
        onEnter();

        if (entryPoint == 0) {
            if (mInitialStateClass != null) {
                return transitTo(mInitialStateClass);
            } else {
                return (T_State) this;
            }
        } else {
            return enterEntryPoint(entryPoint);
        }
    }

    /**
     * Invoked by {@link StateEngine#exit(StateEngine)}.
     */
    protected void onExit() {
        // By default do nothing
    }

    /**
     * Invoked by {@link StateEngine#enter(int)}.
     */
    protected void onEnter() {
        // By default do nothing
    }

    /**
     * Enters the specified entry point. If a state implementation has one or more entry points, it
     * has to override this method.
     *
     * @param entryPoint The index of the entry point to be entered.
     * @return The current state after entering the deep history point.
     */
    protected T_State enterEntryPoint(final int entryPoint) {
        throw new IllegalStateException(Error.ERROR_UNHANDLED_ENTRY_POINT.getDescription(entryPoint, getClass().getSimpleName()));
    }

    /**
     * Enters a deep history point.
     *
     * @return The current state after entering the deep history point.
     */
    @SuppressWarnings("unchecked")
    protected final T_State enterDeepHistory() {
        if (mCurrentState != null) {
            return (T_State) mCurrentState.enterDeepHistory();
        } else {
            if (mInitialStateClass != null) {
                return transitTo(mInitialStateClass);
            } else {
                return (T_State) this;
            }
        }
    }

    /**
     * Enters a shallow history point.
     *
     * @return The current state after entering the shallow history point.
     */
    @SuppressWarnings("unchecked")
    protected final T_State enterShallowHistory() {
        if (mCurrentState != null) {
            return mCurrentState;
        } else {
            if (mInitialStateClass != null) {
                return transitTo(mInitialStateClass);
            } else {
                return (T_State) this;
            }
        }
    }

    /**
     * Invoked to exit the state  represented by this instance of {@link StateEngine}. The state
     * machine will enter the given new target state.
     *
     * @param newState A new target state.
     */
    @SuppressWarnings("unchecked")
    protected final void exit(final T_State newState) {

        if (!isStateEngine()) {
            onExit();

            if (mSuperState != null && mSuperState != getStateEngine()) {
                mSuperState.mCurrentState = this;
            }

            if (mSuperState != null && newState != mSuperState) {
                if (!mSuperState.isStateEngine() && !mSuperState.isSuperStateFor(newState)) {
                    mSuperState.exit(newState);
                }
            }
        }
    }

    /**
     * Invoked when the specified error has occurred while in the given state.
     *
     * @param state A state object.
     * @param error An {@link Error} value specifying the occurred error.
     */
    protected final void onError(final T_State state, final Error error) {
        final Object[] args = {state};
        final String message = error.getDescription(args);
        getObserver().onError(this, error, message);
    }

    /**
     * /**
     * Invoked when the specified error has occurred while in the named event has been received in
     * the given state.
     *
     * @param state     A state object.
     * @param error     An {@link Error} value specifying the occurred error.
     * @param eventName The name of an event.
     */
    protected final void onError(final T_State state, final Error error, final String eventName) {
        final Object[] args = {eventName, state};
        final String message = error.getDescription(args);
        getObserver().onError(this, error, message);
    }

    /**
     * Invoked by {@link StateEngine#dispose()} for an instance of {@link StateEngine} that
     * represents a state machine.
     */
    protected void onDisposeStateEngine() {
    }

    /**
     * Invoked by {@link StateEngine#dispose()} for an instance of {@link StateEngine} that
     * represents a state of a state machine.
     */
    protected void onDisposeState() {
        // By default do nothing
    }

    /**
     * Disposes this {@link StateEngine} and the all state objects contained by it. The method is
     * recursively invoked to all substates of a each state.
     */
    protected final void dispose() {

        if (isStateEngine()) {
            getObserver().onDispose(this);
            onDisposeStateEngine();
        } else {
            onDisposeState();
        }

        for (final T_State state : mSubStates) {
            state.dispose();
        }

        mSubStates.clear();

        if (mStateCache != null) {
            mStateCache.clear();
        }
    }

    /**
     * Starts this {@link StateEngine}. When started, the top-level initial state is entered.
     */
    public synchronized final void start() {
        mCurrentState = transitTo(mInitialStateClass);
        getObserver().onStart(this);
    }

    /**
     * Resets this {@link StateEngine}. Resetting clear the cache of state machines. A state machine
     * has to be started again after resetting.
     */
    public synchronized final void reset() {
        final T_State stateEngine = getStateEngine();

        if (isStateEngine()) {
            mStateCache.clear();
            mStateCache.put(stateEngine.getClass(), stateEngine);
            getObserver().onReset(this);
        } else {
            stateEngine.reset();
        }
    }

    /**
     * Stops this {@link StateEngine}. Stopping causes the state machine and all of its state to be
     * disposed.
     */
    public synchronized final void stop() {
        getObserver().onStop(this);
        dispose();
    }

    /**
     * Tests if the given state object is a direct or an indirect super state of the state
     * represented by this instance of {@link StateEngine}.
     *
     * @param state A state object to be tested.
     * @return A {@code boolean} value.
     */
    @SuppressWarnings("unchecked")
    public final boolean isSuperState(final T_State state) {

        if (state != null) {

            if (state == this) {
                return false;
            }

            if (state == mSuperState) {
                return true;
            } else if (mSuperState != null) {
                return mSuperState.isSuperState(state);
            }
        }
        return false;
    }

    /**
     * Tests if the state object represented by this instance of {@link StateEngine} is a direct or
     * an indirect super state of the given state.
     *
     * @param state A state object to be tested.
     * @return A {@code boolean} value.
     */
    @SuppressWarnings("unchecked")
    protected final boolean isSuperStateFor(final T_State state) {
        return !state.isStateEngine() && (this == state.getSuperState() || isSuperStateFor((T_State) state.getSuperState()));
    }


    /**
     * A {@link String} representation of this {@link StateEngine} is simply the name of the class
     * implementing abstract class {@link StateEngine}.
     *
     * @return A {@link String}
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}