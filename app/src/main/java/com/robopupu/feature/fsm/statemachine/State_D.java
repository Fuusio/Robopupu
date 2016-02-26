/*
 * Copyright (C) 2016 Marko Salmela, http://fuusio.org
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

public class State_D extends State {

    public State_D() {
        super(State.class, null);
    }

    @Override
    public void toB() {
        toState(State_B.class, 3);
    }

    @Override
    public void toSelf() {
        getController().onShowMessage("Event received in D");
    }

    @Override
    protected void onEnter() {
        getController().onShowMessage("Entered State D");
    }

    @Override
    protected void onExit() {
        getController().onShowMessage("Exited State D");
    }
}
