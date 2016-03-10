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

import com.robopupu.api.fsm.StateMachineEvents;

@StateMachineEvents(SimpleStateMachine.class)
public interface TriggerEvents {

    void toB();
    void toCorD(int selector);
    void toB1();
    void toB2();
    void toB3();
    void toSelf();
}
