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

import java.util.ArrayList;

public class CoffeeMaker {

    private final ArrayList<String> mTraces;

    private boolean mHeaterOn;
    private boolean mPowerOn;
    private boolean mValveOpen;

    public CoffeeMaker() {
        mTraces = new ArrayList<>();
        mHeaterOn = false;
        mPowerOn = false;
        mValveOpen = false;
    }

    public void powerOn() {
        mPowerOn = true;
    }

    public void powerOff() {
        mPowerOn = false;
    }

    public void openWaterValve() {
        mValveOpen = true;
    }

    public void closeWaterValve() {
        mValveOpen = false;
    }

    public void switchHeaterOn() {
        mHeaterOn = true;
    }

    public void switchHeaterOff() {
        mHeaterOn = true;
    }

    public void addTrace(final String trace) {
        mTraces.add(trace);
    }

    public ArrayList<String> getTraces() {
        return mTraces;
    }

    public boolean isHeaterOn() {
        return mHeaterOn;
    }

    public boolean isPowerOn() {
        return mPowerOn;
    }

    public boolean isValveOpen() {
        return mValveOpen;
    }
}
