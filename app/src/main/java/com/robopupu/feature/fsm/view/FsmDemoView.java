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
package com.robopupu.feature.fsm.view;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.robopupu.feature.fsm.presenter.FsmDemoPresenter;

import com.robopupu.api.mvp.View;
import com.robopupu.api.plugin.PlugInterface;

@PlugInterface
public interface FsmDemoView extends View {

    void disableTrigger(FsmDemoPresenter.TransitionId id);

    void enableTrigger(FsmDemoPresenter.TransitionId id);

    void setEnabledTriggers(FsmDemoPresenter.TransitionId... ids);

    void resetView();

    void showMessage(@StringRes final int message);

    void showMessage(String message);

    void setStateMachineImage(@DrawableRes final int imageResId);

    void setStartButtonEnabled(boolean enabled);

    void setStopButtonEnabled(boolean enabled);

    void setResetButtonEnabled(boolean enabled);

    void setStartButtonSelected();

    void setStopButtonSelected();

    void setResetButtonSelected();
}
