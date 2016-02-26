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
package com.robopupu.feature.fsm;

import com.robopupu.app.RobopupuAppScope;
import com.robopupu.feature.fsm.presenter.FsmDemoPresenter;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.feature.AbstractFeature;
import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.plugin.Plugin;

@Plugin
public class FsmDemoFeatureImpl extends AbstractFeature implements FsmDemoFeature {

    @Scope(RobopupuAppScope.class)
    @Provides(FsmDemoFeature.class)
    public FsmDemoFeatureImpl() {
        super(FsmDemoScope.class);
    }

    @Override
    protected void onStart() {
        showView(FsmDemoPresenter.class);
    }
}
