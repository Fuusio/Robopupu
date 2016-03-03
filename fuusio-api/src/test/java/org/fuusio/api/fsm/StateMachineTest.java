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

import android.test.suitebuilder.annotation.SmallTest;

import org.fuusio.api.fsm.state.CoffeeReadyState;
import org.fuusio.api.fsm.state.FillingWaterTankState;
import org.fuusio.api.fsm.state.FilteringCoffeeState;
import org.fuusio.api.fsm.state.IdleState;
import org.fuusio.api.fsm.state.MakingCoffeeState;
import org.fuusio.api.fsm.state.PowerOffState;
import org.fuusio.api.fsm.state.PowerOnState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SmallTest
public class StateMachineTest {

    private static final String[] EXPECTED_TRACE = {
            "ENTER: " + PowerOffState.class.getSimpleName(),
            "EXIT: " + PowerOffState.class.getSimpleName(),
            "ENTER: " + PowerOnState.class.getSimpleName(),
            "ENTER: " + IdleState.class.getSimpleName(),
            "EXIT: " + IdleState.class.getSimpleName(),
            "ENTER: " + MakingCoffeeState.class.getSimpleName(),
            "ENTER: " + FillingWaterTankState.class.getSimpleName(),
            "EXIT: " + FillingWaterTankState.class.getSimpleName(),
            "ENTER: " + FilteringCoffeeState.class.getSimpleName(),
            "EXIT: " + FilteringCoffeeState.class.getSimpleName(),
            "EXIT: " + MakingCoffeeState.class.getSimpleName(),
            "ENTER: " + CoffeeReadyState.class.getSimpleName(),
            "EXIT: " + CoffeeReadyState.class.getSimpleName(),
            "EXIT: " + PowerOnState.class.getSimpleName(),
            "ENTER: " + PowerOffState.class.getSimpleName(),
    };

    private TestStateMachine mStateMachine;


    @Before
    public void beforeTests() {
        final CoffeeMaker coffeeMaker = new CoffeeMaker();
        mStateMachine = new TestStateMachine(coffeeMaker);
    }

    @Test
    public void test() {

        // Start the state machine

        mStateMachine.start();

        final State stateEngine = mStateMachine.getStateEngine();

        assertEquals(PowerOffState.class, mStateMachine.getCurrentState().getClass());

        stateEngine.switchPowerOn();
        stateEngine.makeButtonPressed();
        stateEngine.waterTankFull();
        stateEngine.waterTankEmpty();
        stateEngine.switchPowerOff();

        final List<String> traces = stateEngine.getCoffeeMaker().getTraces();

        assertEquals(traces.size(), EXPECTED_TRACE.length);

        for (int i = 0; i < EXPECTED_TRACE.length; i++) {
            assertEquals(traces.get(i), EXPECTED_TRACE[i]);
        }

        final PowerOnState powerOnState = stateEngine.getStateInstance(PowerOnState.class);
        final MakingCoffeeState makingCoffeeState = stateEngine.getStateInstance(MakingCoffeeState.class);
        final FilteringCoffeeState filteringCoffeeState = stateEngine.getStateInstance(FilteringCoffeeState.class);

        assertTrue(powerOnState.notStateMachine());
        assertTrue(makingCoffeeState.notStateMachine());
        assertTrue(filteringCoffeeState.notStateMachine());
        assertFalse(stateEngine.notStateMachine());

        assertNotNull(powerOnState);
        assertNotNull(makingCoffeeState);
        assertNotNull(filteringCoffeeState);

        assertTrue(filteringCoffeeState.isSuperState(makingCoffeeState));
        assertTrue(makingCoffeeState.isSuperState(powerOnState));
        assertTrue(filteringCoffeeState.isSuperState(powerOnState));
        assertFalse(makingCoffeeState.isSuperState(filteringCoffeeState));

        stateEngine.stop();

        assertFalse(stateEngine.hasSubstates());
        assertFalse(powerOnState.hasSubstates());
        assertFalse(makingCoffeeState.hasSubstates());
        assertFalse(filteringCoffeeState.hasSubstates());

        assertTrue(stateEngine.isDisposed());
        assertTrue(powerOnState.isDisposed());
        assertTrue(makingCoffeeState.isDisposed());
        assertTrue(filteringCoffeeState.isDisposed());
    }

    @After
    public void afterTests() {
    }
}
