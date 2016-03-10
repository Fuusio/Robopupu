/*
 * Copyright (C) 2016 Marko Salmela.
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

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * {@link StateMachineEvents} is used to annotate interface which defines methods that are used as
 * events triggering state transitions for a state machine implementation.
 */
@Target(ElementType.TYPE)
public @interface StateMachineEvents {
    Class<? extends StateMachine> value();
}
