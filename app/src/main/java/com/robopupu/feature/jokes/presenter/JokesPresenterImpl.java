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
package com.robopupu.feature.jokes.presenter;

import com.robopupu.api.dependency.Provides;
import com.robopupu.api.feature.AbstractFeaturePresenter;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.component.AppManager;
import com.robopupu.feature.jokes.component.JokesInteractor;
import com.robopupu.feature.jokes.model.JokeResponse;
import com.robopupu.feature.jokes.view.JokesView;

@Plugin
public class JokesPresenterImpl extends AbstractFeaturePresenter<JokesView>
        implements JokesPresenter {

    @Plug AppManager appManager;
    @Plug JokesInteractor interactor;
    @Plug JokesView view;

    @Provides(JokesPresenter.class)
    public JokesPresenterImpl() {
    }

    @Override
    public JokesView getViewPlug() {
        return view;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        super.onPlugged(bus);
        plug(JokesView.class);
    }

    private void displayJoke(final JokeResponse response) {
        final String joke = response.getValue().getJoke();
        final String formattedJoke = joke.replace("&quot;", "\"");
        view.displayJoke(formattedJoke);
    }

    @Override
    public void onRequestJokeClick() {
        // TODO RxJava 2 & Retrofit 2
    }
}
