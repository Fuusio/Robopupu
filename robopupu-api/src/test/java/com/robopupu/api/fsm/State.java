/*
 * Copyright (C) 2010 - 2015 Marko Salmela, http://robopupu.com
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

public class State extends StateEngine<State>
        implements BrewCoffeeButtonEvents, PowerSwitchEvents, WaterTankSensorEvents {

    private CoffeeMaker coffeeMaker;
    private boolean disposed; // For testing

    private State(final Class<? extends State> initialStateClass) {
        super(initialStateClass);
        disposed = false;
    }

    protected State(final Class<? extends State> superStateClass, final Class<? extends State> initialStateClass) {
        super(superStateClass, initialStateClass);
    }

    public static State create(final Class<? extends State> initialStateClass) {
        return new State(initialStateClass);
    }

    public final CoffeeMaker getCoffeeMaker() {
        if (isStateEngine()) {
            return coffeeMaker;
        } else {
            return getStateEngine().getCoffeeMaker();
        }
    }

    public final void setCoffeeMaker(CoffeeMaker coffeeMaker) {
        this.coffeeMaker = coffeeMaker;
    }

    @Override
    public void makeButtonPressed() {
        dispatch().makeButtonPressed();
    }

    @Override
    public void switchPowerOn() {
        dispatch().switchPowerOn();
    }

    @Override
    public void switchPowerOff() {
        dispatch().switchPowerOff();
    }

    @Override
    public void waterTankFull() {
        dispatch().waterTankFull();
    }

    @Override
    public void waterTankEmpty() {
        dispatch().waterTankEmpty();
    }

    // The following methods are test utilities

    public boolean hasSubstates() {
        return !subStates.isEmpty();
    }

    @SuppressWarnings("unchecked")
    public <T extends State> T getStateInstance(final Class<? extends State> stateClass) {
        return (T) this.getState(stateClass);
    }

    public boolean isDisposed() {
        return disposed;
    }

    @Override
    protected void onDisposeStateEngine() {
        disposed = true;
    }

    @Override
    protected void onDisposeState() {
        disposed = true;
    }

    public boolean notStateMachine() {
        return !isStateEngine();
    }
}
