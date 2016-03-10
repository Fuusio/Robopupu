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

/**
 * {@link StateEngineObserver} defines an observer interface for {@link StateEngine}.
 */
public interface StateEngineObserver {

    /**
     * Invoked when the observed {@link StateEngine} is being disposed.
     * @param stateEngine The observed {@link StateEngine}.
     */
    void onDispose(StateEngine stateEngine);

    /**
     * Invoked when the specified error is deteceted in the observed {@link StateEngine}.
     * @param stateEngine The observed {@link StateEngine}.
     * @param error An {@link Error}.
     * @param message A error message.
     */
    void onError(StateEngine stateEngine, StateEngine.Error error, String message);

    /**
     * Invoked when the observed {@link StateEngine} is resetted.
     * @param stateEngine The observed {@link StateEngine}.
     */
    void onReset(StateEngine stateEngine);

    /**
     * Invoked when the observed {@link StateEngine} is started.
     * @param stateEngine The observed {@link StateEngine}.
     */
    void onStart(StateEngine stateEngine);

    /**
     * Invoked when the observed {@link StateEngine} is stopped.
     * @param stateEngine The observed {@link StateEngine}.
     */
    void onStop(StateEngine stateEngine);

}
