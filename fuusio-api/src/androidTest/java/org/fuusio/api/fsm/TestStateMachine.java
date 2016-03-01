/*
 * Copyright (C) 2010 - 2015 Marko Salmela, http://fuusio.org
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
package org.fuusio.api.fsm;

import org.fuusio.api.fsm.state.PowerOffState;

public class TestStateMachine extends StateEngine<TestStateMachine>
        implements BrewCoffeeButtonEvents, PowerSwitchEvents, WaterTankSensorEvents {

    private CoffeeMaker mCoffeeMaker;
    private boolean mDisposed;

    public TestStateMachine(final CoffeeMaker coffeeMaker) {
        super(PowerOffState.class);
        setCoffeeMaker(coffeeMaker);
        mDisposed = false;
    }

    protected TestStateMachine(final Class<? extends TestStateMachine> superStateClass, final Class<? extends TestStateMachine> initialStateClass) {
        super(superStateClass, initialStateClass);
    }

    public final CoffeeMaker getCoffeeMaker() {
        return mCoffeeMaker;
    }

    public final void setCoffeeMaker(CoffeeMaker coffeeMaker) {
        mCoffeeMaker = coffeeMaker;
    }

    @Override
    public void makeButtonPressed() {
        if (isStateEngine()) {
            mCurrentState.makeButtonPressed();
        } else if (mSuperState != getStateEngine()) {
            mSuperState.makeButtonPressed();
        } else {
            onError(this, Error.ERROR_UNHANDLED_EVENT, "makeButtonPressed");
        }
    }

    @Override
    public void switchPowerOn() {
        if (isStateEngine()) {
            mCurrentState.switchPowerOn();
        } else if (mSuperState != getStateEngine()) {
            mSuperState.switchPowerOn();
        } else {
            onError(this, Error.ERROR_UNHANDLED_EVENT, "switchPowerOn");
        }
    }

    @Override
    public void switchPowerOff() {
        if (isStateEngine()) {
            mCurrentState.switchPowerOff();
        } else if (mSuperState != getStateEngine()) {
            mSuperState.switchPowerOff();
        } else {
            onError(this, Error.ERROR_UNHANDLED_EVENT, "switchPowerOff");
        }
    }

    @Override
    public void waterTankFull() {
        if (isStateEngine()) {
            mCurrentState.waterTankFull();
        } else if (mSuperState != getStateEngine()) {
            mSuperState.waterTankFull();
        } else {
            onError(this, Error.ERROR_UNHANDLED_EVENT, "waterTankFull");
        }
    }

    @Override
    public void waterTankEmpty() {
        if (isStateEngine()) {
            mCurrentState.waterTankEmpty();
        } else if (mSuperState != getStateEngine()) {
            mSuperState.waterTankEmpty();
        } else {
            onError(this, Error.ERROR_UNHANDLED_EVENT, "waterTankEmpty");
        }
    }

    // The following methods are test utilities

    public boolean hasSubstates() {
        return !mSubStates.isEmpty();
    }

    @SuppressWarnings("unchecked")
    public <T extends TestStateMachine> T getStateInstance(final Class<? extends TestStateMachine> stateClass) {
        return (T) this.getState(stateClass);
    }

    public boolean isDisposed() {
        return mDisposed;
    }

    protected void onDisposeStateMachine() {
        mDisposed = true;
    }

    public boolean notStateMachine() {
        return !isStateEngine();
    }
}
