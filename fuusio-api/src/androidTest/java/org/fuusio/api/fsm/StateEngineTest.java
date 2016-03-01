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

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
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
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class StateEngineTest extends AndroidTestCase {

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
    }

    @Test
    public void test() {

        final CoffeeMaker coffeeMaker = new CoffeeMaker();

        mStateMachine = new TestStateMachine(coffeeMaker);

        // Start the state machine

        mStateMachine.start();

        assertEquals(PowerOffState.class, mStateMachine.getCurrentState().getClass());

        mStateMachine.switchPowerOn();
        mStateMachine.makeButtonPressed();
        mStateMachine.waterTankFull();
        mStateMachine.waterTankEmpty();
        mStateMachine.switchPowerOff();

        final List<String> traces = coffeeMaker.getTraces();

        assertEquals(traces.size(), EXPECTED_TRACE.length);

        for (int i = 0; i < EXPECTED_TRACE.length; i++) {
            assertEquals(traces.get(i), EXPECTED_TRACE[i]);
        }

        final PowerOnState powerOnState = mStateMachine.getStateInstance(PowerOnState.class);
        final MakingCoffeeState makingCoffeeState = mStateMachine.getStateInstance(MakingCoffeeState.class);
        final FilteringCoffeeState filteringCoffeeState = mStateMachine.getStateInstance(FilteringCoffeeState.class);

        assertTrue(powerOnState.notStateMachine());
        assertTrue(makingCoffeeState.notStateMachine());
        assertTrue(filteringCoffeeState.notStateMachine());
        assertFalse(mStateMachine.notStateMachine());

        assertNotNull(powerOnState);
        assertNotNull(makingCoffeeState);
        assertNotNull(filteringCoffeeState);

        assertTrue(filteringCoffeeState.isSuperState(makingCoffeeState));
        assertTrue(makingCoffeeState.isSuperState(powerOnState));
        assertTrue(filteringCoffeeState.isSuperState(powerOnState));
        assertFalse(makingCoffeeState.isSuperState(filteringCoffeeState));


        mStateMachine.stop();

        assertFalse(mStateMachine.hasSubstates());
        assertFalse(powerOnState.hasSubstates());
        assertFalse(makingCoffeeState.hasSubstates());
        assertFalse(filteringCoffeeState.hasSubstates());

        assertTrue(mStateMachine.isDisposed());
        assertTrue(powerOnState.isDisposed());
        assertTrue(makingCoffeeState.isDisposed());
        assertTrue(filteringCoffeeState.isDisposed());
    }

    @After
    public void afterTests() {
    }
}
