/*
 * Copyright (C) 2016 Marko Salmela, http://robopupu.com
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
package com.robopupu.feature.fsm.statemachine;

public class State_C extends State {

    public State_C() {
        super(State.class, null);
    }

    @Override
    public void toB() {
        toHistoryState(State_B.class, false);
    }

    @Override
    protected void onEnter() {
        getController().onShowMessage("Entered State C");
    }

    @Override
    protected void onExit() {
        getController().onShowMessage("Exited State C");
    }

}
