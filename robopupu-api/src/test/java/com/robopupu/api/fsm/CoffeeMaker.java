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

import java.util.ArrayList;

public class CoffeeMaker {

    private final ArrayList<String> traces;

    private boolean heaterOn;
    private boolean powerOn;
    private boolean valveOpen;

    public CoffeeMaker() {
        traces = new ArrayList<>();
        heaterOn = false;
        powerOn = false;
        valveOpen = false;
    }

    public void powerOn() {
        powerOn = true;
    }

    public void powerOff() {
        powerOn = false;
    }

    public void openWaterValve() {
        valveOpen = true;
    }

    public void closeWaterValve() {
        valveOpen = false;
    }

    public void switchHeaterOn() {
        heaterOn = true;
    }

    public void switchHeaterOff() {
        heaterOn = true;
    }

    public void addTrace(final String trace) {
        traces.add(trace);
    }

    public ArrayList<String> getTraces() {
        return traces;
    }

    public boolean isHeaterOn() {
        return heaterOn;
    }

    public boolean isPowerOn() {
        return powerOn;
    }

    public boolean isValveOpen() {
        return valveOpen;
    }
}
